package top.whitecola.siesta;

import com.sun.javafx.stage.StageHelper;
import top.whitecola.siesta.loader.AppClassloader;

import java.io.File;
import java.net.URI;
import java.util.List;

public class SiestaContext {
    private static SiestaContext siestaContext = new SiestaContext();
    private List<String> needToLoadClazz;
    private AppClassloader loader;

    private SiestaContext(){ }


    public void runApplication() {
        String thePackage = new Exception().getStackTrace()[1].toString();
        thePackage = thePackage.substring(0,thePackage.lastIndexOf("."));
        needToLoadClazz = scan(thePackage);
        loader = new AppClassloader(needToLoadClazz);
        Thread.currentThread().setContextClassLoader(loader);
    }

    public static SiestaContext getSiestaContext() {
        return siestaContext;
    }

    private List<String> scan(String thePackage){
        return null;
    }


}
