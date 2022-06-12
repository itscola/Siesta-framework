package top.whitecola.test;

import top.whitecola.siesta.SiestaContext;

public class MainTest {
    public static void main(String[] args) {
        SiestaContext.getSiestaContext().runApplication(Main.class,args);
    }
}
