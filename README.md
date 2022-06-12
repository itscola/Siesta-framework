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
