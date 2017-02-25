
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 ** Note: This tool does not support command includes '|'
 ** @author led
 **
 **/

public class ShellExecutor {
    private static String output;
    private static String outputErr;
    
    public ShellExecutor () {
    }
    
    public void showCmd(String cmd) {
        System.out.println("The input command is " + cmd);
    }

    public static int executeCommand(String command) throws IOException, InterruptedException {
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
        System.out.println(output);
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
    
    public static int executeCommand(String[] commands) throws IOException, InterruptedException {
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
    
  public static String getOutput() {
      return output;
  }
  
  public static String getOutputErr() {
      return outputErr;
  }
  
  public static void main(String[] args) throws Exception{
    //ShellExecutor se = new ShellExecutor();
    //se.executeCommand("ls -l /var/tmp");
    executeCommand("ls -l /var/tmp");
  }
}

