package top.whitecola.siesta;

import top.whitecola.siesta.annotations.ApplicationMain;
import top.whitecola.siesta.annotations.Component;
import top.whitecola.siesta.annotations.Inject;
import top.whitecola.siesta.listeners.Listener;
import top.whitecola.siesta.loader.AppClassloader;
import top.whitecola.siesta.loader.IAppMain;
import top.whitecola.siesta.utils.InterfaceUtils;

import java.io.File;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SiestaContext extends BeanFactory{
    private static SiestaContext siestaContext = new SiestaContext();
    private AppClassloader loader;
    private ConcurrentHashMap<String,Class<?>> classMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Object> beans = new ConcurrentHashMap<>();
    private String mainClass;
    private String scope;
    private Class<?> siestaApplicationMain;
    private String[] args;

    private SiestaContext(){ }


    public ClassLoader runApplication(Class<?> applicationMain,String[] args) {
        this.siestaApplicationMain = applicationMain;
        this.args = args;

        String thePackages = new Exception().getStackTrace()[1].toString();
        thePackages = thePackages.substring(0,thePackages.lastIndexOf("main")-1);
        scope = thePackages.substring(0,thePackages.lastIndexOf("."));;
        loader = new AppClassloader().setScopePackage(scope);
        Thread.currentThread().setContextClassLoader(loader);
        scan(scope);
        newBeansInstance();
        try {
            invokeMain();
        }catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException e) {
            e.printStackTrace();
        }catch (IllegalClassFormatException e){
            System.out.println(e.getMessage());
        }
        return loader;
    }

    public static SiestaContext getSiestaContext() {
        return siestaContext;
    }

    public void newBeansInstance(){
        for(String beanName : classMap.keySet()){
            getBean(beanName);
        }
    }


    @Override
    public Object getBean(String name){
        Object bean;
        if((bean = this.beans.get(name))==null){
            bean = createBean(name);
        }
        return bean;
    }

    @Override
    public Object getBean(Class<?> clazz){
        return getBean(clazz.getSimpleName());
    }

    @Override
    public <T> T getBean(T clazz) {
        return (T) getBean(clazz.getClass().getSimpleName());
    }

    @Override
    public Object createBean(String name){
        Class<?> beanClass = this.classMap.get(name);
        Object beanObj = this.newInstance(beanClass);
        if(!beanClass.isInterface()&&InterfaceUtils.hasInterface(beanClass, Listener.class)){
            try {
                beanClass.getMethod("onBeanCreate").invoke(beanObj);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        this.beans.put(name, beanObj);
        return beanObj;
    }


    public void scan(String thePackages){
        URL resource = this.getClass().getClassLoader().getResource(thePackages.replace(".","/"));
        if(resource==null){
            return;
        }
        String path = resource.getPath();
        File dir = new File(path);
        for(File file : dir.listFiles()){
            if(file.isDirectory()){
                scan(thePackages+"."+file.getName());
            }else{
                try {

                    registerClass(thePackages+"."+file.getName());
                } catch (ClassNotFoundException e) {
                    continue;
                }
            }

        }
    }

    private void registerClass(String name) throws ClassNotFoundException{
        //下面的问题
        Class<?> clazz = loader.loadClass(name);

        if(!clazz.isAnnotationPresent(Component.class) && !clazz.isAnnotationPresent(ApplicationMain.class)){
            System.out.println(clazz.getName()+" is not a component.");
            return;
        }

        String beanName = clazz.getSimpleName();
        this.classMap.put(beanName, clazz);
    }

    private Object newInstance(Class<?> clazz){
        Object beanObj = null;
        if(clazz==null){
            try {
                this.loader.loadClass(clazz.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            if(clazz.isInterface()){
                return doInject(clazz);
            }

            beanObj = clazz.getDeclaredConstructor().newInstance();
            beanObj = handleInjections(beanObj, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanObj;
    }

    private Object handleInjections(Object beanObj,Class<?> beanClass) throws IllegalArgumentException, IllegalAccessException{

        for(Field field : beanClass.getDeclaredFields()){
            if(!field.isAnnotationPresent(Inject.class)){
                continue;
            }

            field.setAccessible(true);
            Object bean = doInject(field.getType());
            field.set(beanObj, bean);

            if(InterfaceUtils.hasInterface(bean.getClass(),Listener.class)) {
                try {
                    bean.getClass().getMethod("onBeingInjected",Object.class).invoke(bean,beanObj);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            if(InterfaceUtils.hasInterface(beanObj.getClass(),Listener.class)) {
                try {
                    beanObj.getClass().getMethod("onInjectingBean",Object.class).invoke(beanObj,bean);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return beanObj;

    }

    public Object doInject(Class<?> clazz){
        if(clazz.isInterface()){
            for(Map.Entry<String,Class<?>> entry: this.classMap.entrySet()){
                if(clazz.isAssignableFrom(entry.getValue()) && clazz!=entry.getValue()){
                    Object obj = getBean(entry.getValue());
//                    if(InterfaceUtils.hasInterface(obj.getClass(),Listener.class)){
//                        try {
//                            obj.getClass().getMethod("onBeingInjected",Object.class).invoke(obj);
//                        } catch (Throwable e) {
//                            e.printStackTrace();
//                        }
//                    }
                    return obj;
                }
            }
        }

        return getBean(clazz);
    }

    public void invokeMain() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalClassFormatException {
        if(!InterfaceUtils.hasInterface(siestaApplicationMain,IAppMain.class)){
            throw new IllegalClassFormatException("The "+this.siestaApplicationMain.getSimpleName()+" class didn't implement IAppMain interfaces.");
        }
        Object obj = getBean(this.siestaApplicationMain);
        Method mainMethod = obj.getClass().getDeclaredMethod("AppMain",String[].class);
        mainMethod.setAccessible(true);
        mainMethod.invoke(obj,(Object) this.args);
    }




}
