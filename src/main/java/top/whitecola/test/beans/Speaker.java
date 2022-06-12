package top.whitecola.test.beans;

import top.whitecola.siesta.annotations.Component;
import top.whitecola.siesta.listeners.Listener;

@Component
public class Speaker implements ISpeaker, Listener {
    @Override
    public void speak() {
        System.out.println("Hi World.");
        
    }



}
