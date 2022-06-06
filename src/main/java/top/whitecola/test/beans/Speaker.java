package top.whitecola.test.beans;

import top.whitecola.siesta.annotation.Component;

@Component
public class Speaker implements ISpeaker{
    @Override
    public void speak() {
        System.out.println("Hi World.");
        
    }
}
