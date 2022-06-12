package top.whitecola.test;

import top.whitecola.siesta.annotations.ApplicationMain;
import top.whitecola.siesta.annotations.Inject;
import top.whitecola.siesta.listeners.Listener;
import top.whitecola.siesta.loader.IAppMain;
import top.whitecola.test.beans.ISpeaker;

@ApplicationMain
public class Main implements IAppMain, Listener {
//    @Inject
//    private ISpeaker iSpeaker;

    @Inject
    private static ISpeaker iSpeaker;

    @Override
    public void AppMain(String[] args) {
        iSpeaker.speak();
    }

    @Override
    public boolean onBeanCreate() {
        return Listener.super.onBeanCreate();
    }

    @Override
    public boolean onInjectingBean() {
        return Listener.super.onInjectingBean();
    }

    @Override
    public boolean onBeingInjected() {
        return Listener.super.onBeingInjected();
    }
}
