package top.whitecola.test;

import top.whitecola.siesta.annotation.Component;
import top.whitecola.siesta.annotation.Inject;
import top.whitecola.test.beans.ISpeaker;
import top.whitecola.test.beans.Speaker;

@Component
public class Test {
    @Inject
    private ISpeaker speaker;

    public void say(){
        speaker.speak();
    }
}
