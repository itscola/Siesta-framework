package top.whitecola.test;

import top.whitecola.siesta.SiestaContext;
import top.whitecola.siesta.annotation.Component;
import top.whitecola.siesta.annotation.Inject;
import top.whitecola.siesta.loader.AppClassloader;
import top.whitecola.test.beans.ISpeaker;
import top.whitecola.test.beans.Speaker;

public class MainTest {
    public static void main(String[] args) {
        SiestaContext.getSiestaContext().runApplication(Main.class,args);
    }
}
