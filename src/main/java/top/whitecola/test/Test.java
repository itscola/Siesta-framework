package top.whitecola.test;

import top.whitecola.siesta.annotation.Component;
import top.whitecola.siesta.annotation.Inject;
import top.whitecola.test.beans.ISpeaker;

@Component
public class Test {
    private ISpeaker speaker;

    public void say(){
        speaker.speak();
    }
}
