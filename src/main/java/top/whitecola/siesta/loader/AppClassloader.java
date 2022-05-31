package top.whitecola.siesta.loader;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

@Data
public class AppClassloader extends ClassLoader {
    private ClassLoader parent = this.getClass().getClassLoader();
    private List<String> loadList;
    private List<String> afterHookLoading = new ArrayList<>();

    public AppClassloader(List<String> loadList){
        super(null);
        this.loadList = loadList;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        for(String theName : loadList){
            if(name.equals(theName)){
                Class<?> clazz = findClass(name);

            }
        }
        return super.loadClass(name, resolve);
    }




}
