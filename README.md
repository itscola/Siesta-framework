# Siesta
A lightweight framework for Java application .     
**In development.**

## demo:
```java
@ApplicationMain
public class Main implements IAppMain {
//    @Inject
//    private ISpeaker iSpeaker;

    @Inject
    private static ISpeaker iSpeaker;

    @Override
    public void appMain(String[] args) {
        iSpeaker.speak();
    }
}
```

```java
@Component
public class Speaker implements ISpeaker, Listener {
    @Override
    public void speak() {
        System.out.println("Hi World.");
        
    }
    
    // The Listener interface has the default method. The method doesn't HAVE TO be Override even if it implements Listener.
    @Override
    public boolean onBeingInjected(Object bean) {
        System.out.println("The bean has been injected by a bean named "+bean.getClass().getSimpleName());
        return Listener.super.onBeingInjected(bean);
    }
    
    // The Listener interface has the default method. The method doesn't HAVE TO be Override even if it implements Listener.
    @Override
    public boolean onInjectingBean(Object bean) {
        System.out.println("The bean has injected into a bean named "+bean.getClass().getSimpleName());
        return Listener.super.onInjectingBean(bean);
    }

    // The Listener interface has the default method. The method doesn't HAVE TO be Override even if it implements Listener.
//    @Override
//    public boolean onBeanCreate() {
//        System.out.println("The bean has been created.");
//        return Listener.super.onBeanCreate();
//    }
}
```
