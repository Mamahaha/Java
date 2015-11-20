package org.led.tools.MultiThreads.MultiProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MultiProcess {
    /*
     * The 2 ways of creating a new process have the same result. 
     * Both of them will block the major process. In other word, they run in SYNC mode. 
    */
    
    public void createProcess(String[] cmds) {
        try {
            Process p = Runtime.getRuntime().exec(cmds);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s = null;
            while((s=stdInput.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void createProcess2(String[] cmds) {
        ProcessBuilder pb = new ProcessBuilder(cmds);
        try {
            Process p = pb.start();
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s = null;
            while((s=stdInput.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {           
            e.printStackTrace();
        }
    }
    
    public static void test() {
        MultiProcess mp = new MultiProcess();
        String[] cmds = {"ls", "-l", "/opt"};
        mp.createProcess(cmds);
        System.out.println("running p1");
        mp.createProcess2(cmds);
        System.out.println("running p2");
    }
}
