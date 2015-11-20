package org.led.tools.UnitTest;


public class Base {
    private int i;

    public int getI() {
        System.out.println("Base getI");
        return i;
    }

    public void setI(int i) {
        System.out.println("Base setI");
        this.i = i;
    }    
}
