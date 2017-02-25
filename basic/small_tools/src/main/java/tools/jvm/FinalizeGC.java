package tools.jvm;

public class FinalizeGC {
    public static FinalizeGC hook = null;
    
    private void isAlive(int n) {
        System.out.println("I'm alive: " + n);
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Finalize is ongoing");
        hook = this;
    }
    
    public static void run() throws InterruptedException {
        hook = new FinalizeGC();
        hook.isAlive(1);
        
        hook = null;
        System.gc();
        Thread.sleep(500);
        
        if (hook != null) {
            hook.isAlive(2);
        } else {
            System.out.println("Dead!");
        }
        
        hook = null;
        System.gc();
        Thread.sleep(500);
        
        if (hook != null) {
            hook.isAlive(3);
        } else {
            System.out.println("Dead!");
        }
    }
}
