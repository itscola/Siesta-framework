package top.whitecola.siesta.utils;

import java.util.Arrays;

public class InterfaceUtils {
    public static boolean hasInterface(Class<?> clazz, Class<?> theInterface){
        Class<?>[] interfaces = clazz.getInterfaces();
        if(clazz.equals(theInterface)){
            return false;
        }


        for(Class<?> iInterface : Arrays.asList(interfaces)){
            if(iInterface.equals(theInterface)){
                return true;
            }
        }

        return false;
    }
}
