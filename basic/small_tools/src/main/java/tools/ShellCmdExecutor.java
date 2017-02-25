package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Note: This tool does not support command includes '|'
 * @author led
 *
 */
public class ShellCmdExecutor{
    private String output;
    private String outputErr;
    
    private static ShellCmdExecutor instance;
    
    private ShellCmdExecutor () {
    }
    
    public static ShellCmdExecutor getInstance() {
        if (instance == null) {
            instance = new ShellCmdExecutor();
        }
        return instance;
    }
    
    public int executeCommand(String command) throws IOException, InterruptedException {
        Process p;
        int exitVal = 0;
        p = Runtime.getRuntime().exec(command);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder outputBuilder = new StringBuilder();
        String str = "";
        while ((str = bufferedReader.readLine()) != null) {
            outputBuilder.append(str + "\n");
        }
        output = outputBuilder.toString();

        BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        StringBuilder outputErrBuilder = new StringBuilder();
        String strErr = "";
        while ((strErr = brError.readLine()) != null) {
            outputErrBuilder.append(strErr + "\n");
        }
        outputErr = outputErrBuilder.toString();

        exitVal = p.waitFor();
        return exitVal;
    }
    
    public int executeCommand(String[] commands) throws IOException, InterruptedException {
        Process p;
        int exitVal = 0;
        p = Runtime.getRuntime().exec(commands);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder outputBuilder = new StringBuilder();
        String str = "";
        while ((str = bufferedReader.readLine()) != null) {
            outputBuilder.append(str + "\n");
        }
        output = outputBuilder.toString();

        BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        StringBuilder outputErrBuilder = new StringBuilder();
        String strErr = "";
        while ((strErr = brError.readLine()) != null) {
            outputErrBuilder.append(strErr + "\n");
        }
        outputErr = outputErrBuilder.toString();

        exitVal = p.waitFor();
        return exitVal;
    }
    
  public String getOutput() {
      return output;
  }
  
  public String getOutputErr() {
      return outputErr;
  }
}

