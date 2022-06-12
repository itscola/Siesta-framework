package top.whitecola.test.beans;

import top.whitecola.siesta.annotations.Component;

@Component
public class Speaker implements ISpeaker{
    @Override
    public void speak() {
        System.out.println("Hi World.");
        
    }
}
