package top.whitecola.siesta.loader;

import top.whitecola.siesta.SiestaContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

public class AppClassloader extends ClassLoader {
    private ClassLoader parent = this.getClass().getClassLoader();
    private List<String> parentClasses = Arrays.asList("java.");
    // private SiestaContext context;
    private IClassHandler handler;
    private String scope;


    public AppClassloader(){
        super(null);
        // this.context = context;
        this.handler = new ClassHandler();
        // this.parentClasses = parentLoadList;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(!name.startsWith(this.scope)){
            return this.parent.loadClass(name);
        }

        Class<?> loaddedClass = findLoadedClass(name);
        if(loaddedClass!=null){
            return loaddedClass;
        }

        String path = name.replace(".", "/");
        path = path.substring(0,path.lastIndexOf("/class")) + ".class";

        InputStream is = parent.getResourceAsStream(path);
        byte[] data = null;

        try {
            data = new byte[is.available()];
            is.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(name.replace(".class",""),data,0, data.length);
    }





    public AppClassloader setScopePackage(String scope){
        this.scope = scope;
        return this;
    }





}
