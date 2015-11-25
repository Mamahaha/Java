package org.led.database.client;

import org.led.database.client.manager.StandardConnection;
import org.led.database.client.utils.ConfigLoader;

/**
 * Hello world!
 *
 */
public class MainApp 
{
	private StandardConnection sc = new StandardConnection();
	
	
    public static void main( String[] args )
    {
    	String sqlCmd = ConfigLoader.getValue("sql_cmd");
    	MainApp app = new MainApp();
        
        app.sc.runSqlCmd(sqlCmd);
    
    }
}
