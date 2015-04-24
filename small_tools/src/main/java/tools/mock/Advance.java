package tools.mock;

public class Advance {
    private Base base;

    public Base getBase() {
        System.out.println("Advance getBase");
        return base;
    }

    public void setBase(Base base) {
        System.out.println("Advance setBase");
        this.base = base;
    }
    
    public int getBaseI() {
        System.out.println("Advance getBaseI");
        return base.getI();
    }
    
    public int getIndex(int index) {
        System.out.println("Advance getIndex");
        if(index == 0) {
            return index + 10;
        } else{
            return index;
        }
    }
}
