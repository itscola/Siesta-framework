package top.whitecola.siesta;

import top.whitecola.siesta.annotation.Component;
import top.whitecola.siesta.annotation.Inject;
import top.whitecola.siesta.loader.AppClassloader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class SiestaContext {
    private static SiestaContext siestaContext = new SiestaContext();
    private AppClassloader loader;
    private ConcurrentHashMap<String,Class<?>> classMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Object> beans = new ConcurrentHashMap<>();

    private SiestaContext(){ }


    public void runApplication() {
        String thePackages = new Exception().getStackTrace()[1].toString();
        thePackages = thePackages.substring(0,thePackages.lastIndexOf("."));
        // needToLoadClazz = scan(thePackage);
        loader = new AppClassloader();
        Thread.currentThread().setContextClassLoader(loader);
        scan(thePackages);
        newBeansInstance();
    }

    public static SiestaContext getSiestaContext() {
        return siestaContext;
    }

    public void newBeansInstance(){
        for(String beanName : classMap.keySet()){
            getBean(beanName);
        }
    }


    public Object getBean(String name){
        Object bean;
        if((bean = this.beans.get(name))==null){
            bean = createBean(name);
        }
        return bean;
    }

    public Object createBean(String name){
        Class<?> beanClass = this.classMap.get(name);
        Object beanObj = this.newInstance(beanClass);
        this.beans.put(name, beanObj);
        return beanObj;
    }


    public void scan(String thePackages){
        URL resource = loader.getResource(thePackages);
        File dir = new File(resource.getPath());
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
        Class<?> clazz = loader.loadClass(name);
        if(!clazz.isAnnotationPresent(Component.class)){
            return;
        }

        String beanName = clazz.getSimpleName();
        this.beans.put(beanName, clazz);
    }

    private Object newInstance(Class<?> clazz){
        Object beanObj = null;
        
        // Constructor[] constructors = clazz.getDeclaredConstructors();
        // if(constructors.length!=1){
        //     return null;
        // }

        try {
            beanObj = clazz.getDeclaredConstructor().newInstance();
            beanObj = handleInjections(beanObj, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanObj;
    }

    private Object handleInjections(Object beanObj,Class<?> beanClass) throws IllegalArgumentException, IllegalAccessException{
        for(Field field : beanClass.getDeclaredFields()){
            field.setAccessible(true);
            if(!field.isAnnotationPresent(Inject.class)){
                continue;
            }
            field.set(beanObj, doInject(field));
        }
        return beanObj;

    }

    private Object doInject(Field field){
        
        return null;
    }

    



}
