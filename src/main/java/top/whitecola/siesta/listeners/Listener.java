package top.whitecola.siesta.listeners;

public interface Listener {
    boolean onBeanCreate();
    boolean onInjectingBean();
    boolean onBeingInjected();
}
