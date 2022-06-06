package top.whitecola.test;

import top.whitecola.siesta.SiestaContext;
import top.whitecola.siesta.annotation.Inject;
import top.whitecola.test.beans.ISpeaker;
import top.whitecola.test.beans.Speaker;

public class MainTest {
    public static void main(String[] args) {
        SiestaContext.getSiestaContext().runApplication();
        Test test = null;
        test = (Test) SiestaContext.getSiestaContext().doInject(test.getClass());
        test.say();
    }
}
