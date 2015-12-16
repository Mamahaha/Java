package org.led.tools.unitTest;

public class Expert {
    private Advance advance;

    public Expert(Advance advance) {
        System.out.println("Expert init");
        this.advance = advance;
    }
    
    public Advance getAdvance() {
        System.out.println("Expert getAdvance");
        return advance;
    }

    public void setAdvance(Advance advance) {
        System.out.println("Expert setAdvance");
        this.advance = advance;
    }
    
    public int getBaseI() {
        System.out.println("show " + advance.getBaseI());
        return advance.getBaseI();
    }
}
