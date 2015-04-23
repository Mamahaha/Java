package tools;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    
    private Timer timer = new Timer();
    
    class MyTimerTask extends TimerTask {
        
        private int index;
        private int interval;
        
        public MyTimerTask(int idx, int itl) {
            index = idx;
            interval = itl;
        }
        
        @Override
        public void run() {
            System.out.println("Task " + index + " is running!");
            setTimer();
        }
        
        private void setTimer() {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            timer = new Timer();
            timer.schedule(new MyTimerTask(index+1, interval),  interval);
        }
    }
    
    public void start() {
        timer.schedule(new MyTimerTask(0, 3000),  3000);
    }
    
    public static void main(String[] args) {
        MyTimer myTimer = new MyTimer();
        myTimer.start();
    }
    
    
}
