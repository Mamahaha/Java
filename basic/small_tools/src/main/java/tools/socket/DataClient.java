package tools.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class DataClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        //String serverAddress = JOptionPane.showInputDialog(
        //    "Enter IP Address of a machine that is\n" +
        //    "running the date service on port 9090:");
        if (args.length != 3) {
            System.out.println("===Usage:===");
            System.out.println("java org/led/tools/socket/DataClient <remote_ip> <remote_port> <client_sleep_seconds>");
            return;
        }
        System.out.println("client prestart date: " + new Date().toString() + "; args: " + args[0] + "| " + args[1]);
        Socket s = new Socket(args[0], Integer.parseInt(args[1]));

        System.out.println("client start date: " + new Date().toString());
        //s.setSoTimeout(3000);
        Thread.sleep(Integer.parseInt(args[2]) * 1000);
        System.out.println("client start2 date: " + new Date().toString());
        PrintWriter out =
                new PrintWriter(s.getOutputStream(), true);
            out.println("adfdadfasf");
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        System.out.println("client stop date: " + new Date().toString());
        //JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
    }
}
