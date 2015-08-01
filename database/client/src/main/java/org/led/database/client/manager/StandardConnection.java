package org.led.database.client.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.led.database.client.utils.ConfigLoader;
import org.postgresql.Driver;

public class StandardConnection {
	private String ip = "127.0.0.1";
	private long port = 5432;
	private String ssl = "false";
	private String name = "jpa";
	private String user = "led";
	private String password = "bmc";
	private String urlPrefix = "jdbc:postgresql";
	private String sqlCmd = "";
	
	public StandardConnection() {
		ip = (ConfigLoader.getValue("ip") != null) ? ConfigLoader.getValue("ip") : ip;
		port = (ConfigLoader.getValue("port") != null) ? Integer.parseInt(ConfigLoader.getValue("port")) : port;
		ssl = (ConfigLoader.getValue("ssl") != null) ? ConfigLoader.getValue("ssl") : ssl;
		name = (ConfigLoader.getValue("name") != null) ? ConfigLoader.getValue("name") : name;
		user = (ConfigLoader.getValue("user") != null) ? ConfigLoader.getValue("user") : user;
		password = (ConfigLoader.getValue("password") != null) ? ConfigLoader.getValue("password") : password;
		urlPrefix = (ConfigLoader.getValue("url_prefix") != null) ? ConfigLoader.getValue("url_prefix") : urlPrefix;
		sqlCmd = (ConfigLoader.getValue("sql_cmd") != null) ? ConfigLoader.getValue("sql_cmd") : sqlCmd;
	}
	
	public void runSqlCmd(String sqlCmd) {
		String dbUrl = urlPrefix + "://" + ip + ":" + port + "/" + name;

		try {
			DriverManager.registerDriver(new Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", password);
		if (ssl.equals("true")) {
			props.setProperty("ssl", ssl);
		}
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(dbUrl, props);
			st = conn.createStatement();
			rs = st.executeQuery(sqlCmd);
			
			ResultSetMetaData md = rs.getMetaData();
			int col = md.getColumnCount();
			
			while (rs.next()) {
				for (int i=1; i<=col; i++) {
					System.out.print(rs.getString(i));
					System.out.print("\t");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}		
	}
}
