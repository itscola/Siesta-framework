package top.whitecola.test;

import top.whitecola.siesta.SiestaContext;
import top.whitecola.siesta.annotation.Component;
import top.whitecola.siesta.annotation.Inject;
import top.whitecola.siesta.loader.IAppMain;
import top.whitecola.test.beans.ISpeaker;

@Component
public class Main implements IAppMain {
    @Inject
    ISpeaker iSpeaker;

    @Override
    public void AppMain(String[] args) {
        iSpeaker.speak();
    }
}
