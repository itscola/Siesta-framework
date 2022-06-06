package top.whitecola.siesta.loader;

import top.whitecola.siesta.SiestaContext;

import java.util.Arrays;
import java.util.List;

public class AppClassloader extends ClassLoader {
    private ClassLoader parent = this.getClass().getClassLoader();
    private List<String> parentClasses = Arrays.asList("java.");
    // private SiestaContext context;
    private IClassHandler handler;



    public AppClassloader(){
        super(null);
        // this.context = context;
        this.handler = new ClassHandler();
        // this.parentClasses = parentLoadList;
        
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        
        if(this.isForParentLoader(name)){
            return this.parent.loadClass(name);
        }
        Class<?> loaddedClass = findLoadedClass(name);
        if(loaddedClass!=null){
            return loaddedClass;
        }

        System.out.println(name);

        Class<?> clazz = null;
        try {
            clazz = super.loadClass(name);
            clazz = this.handler.doTransform(clazz);
        } catch (Throwable e) {
            
        }
        
        // context.addToBeansMapIfIs(clazz);
        return clazz;
    }

    protected boolean isForParentLoader(String clazzName){
        for(String name : this.parentClasses){
            if(clazzName.startsWith(name)){
                return true;
            }
        }
        return false;
    }




}
