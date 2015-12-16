package org.led.tools.multiThreads.reentrant;

public class ReentrantReadJob implements Runnable {
    private ReentrantData data;
    private int mode;
    public ReentrantReadJob(ReentrantData data, int mode) {
        this.data = data;
        this.mode = mode;
    }
    
    public void run() {
        switch(mode) {
        case 0:
            this.data.getValue();
            break;
        case 1:
            this.data.getValue0();
            break;
        case 2:
            this.data.getValue1();
            break;
        case 3:
            this.data.getValue2();
            break;
        case 5:
            this.data.getFromQueue();
            break;
        default:
            break;
        }
    }
}
