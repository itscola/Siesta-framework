package top.whitecola.siesta.listeners;

public interface Listener {
    default boolean onBeanCreate(){return true;}
    default boolean onInjectingBean(){return true;}
    default boolean onBeingInjected(){return true;}
}
