package top.whitecola.siesta.interfaces;

import java.util.concurrent.ConcurrentHashMap;

public interface IBeanFactory {
    Object getBean(String name);
    <T> T getBean(T clazz);
    Object createBean(String name);
    Object getBean(Class<?> clazz);
}
