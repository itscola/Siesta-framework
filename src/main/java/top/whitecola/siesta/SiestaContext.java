package top.whitecola.siesta;

import top.whitecola.siesta.annotation.Component;
import top.whitecola.siesta.loader.AppClassloader;

import java.io.File;
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
        newBeanInstances();
    }

    public static SiestaContext getSiestaContext() {
        return siestaContext;
    }

    public void newBeanInstances(){

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
        this.beans.put(beanName, clazz)
    }

    private Object newInstance(Class<?> clazz){
        return null;
    }

    public Object getBean(String name){
        return this.beans.get(name);
    }



}
