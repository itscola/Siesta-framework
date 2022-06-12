package top.whitecola.test;

import top.whitecola.siesta.annotations.Component;
import top.whitecola.siesta.annotations.Inject;
import top.whitecola.test.beans.ISpeaker;

@Component
public class Test {
    @Inject
    private ISpeaker speaker;

    public void say(){
        speaker.speak();
    }
}
