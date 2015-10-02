package org.postgre_tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args )
    {
    	ServerSocket listner = new ServerSocket(8088);
        Socket socket = listener.accept();
        socket.getSoTimeout();
    }
}
