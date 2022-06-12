package top.whitecola.siesta.listeners;

public interface Listener {
    default boolean onBeanCreate(){return true;}
    default boolean onInjectingBean(Object bean){return true;}
    default boolean onBeingInjected(Object bean){return true;}
}
