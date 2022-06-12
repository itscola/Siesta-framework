package top.whitecola.test;

import top.whitecola.siesta.annotations.ApplicationMain;
import top.whitecola.siesta.annotations.Inject;
import top.whitecola.siesta.loader.IAppMain;
import top.whitecola.test.beans.ISpeaker;

@ApplicationMain
public class Main implements IAppMain {
//    @Inject
//    private ISpeaker iSpeaker;

    @Inject
    private static ISpeaker iSpeaker;

    @Override
    public void AppMain(String[] args) {
        iSpeaker.speak();
    }
}
