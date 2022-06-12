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
    public void AppMain(String[] args) {
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
    
    @Override
    public boolean onBeingInjected(Object bean) {
        System.out.println("The bean has been injected by a bean named "+bean.getClass().getSimpleName());
        return Listener.super.onBeingInjected(bean);
    }

    @Override
    public boolean onInjectingBean(Object bean) {
        System.out.println("The bean has injected into a bean named "+bean.getClass().getSimpleName());
        return Listener.super.onInjectingBean(bean);
    }

//    @Override
//    public boolean onBeanCreate() {
//        System.out.println("The bean has been created.");
//        return Listener.super.onBeanCreate();
//    }
}
```
