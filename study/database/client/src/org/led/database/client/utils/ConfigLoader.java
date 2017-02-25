package org.led.database.client.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ConfigLoader {

	private static JSONArray jsonArray = null;

	public static void initContent(String filePath) {
		if (jsonArray == null) {
			String content = loadFile(filePath);
			jsonArray = JSONArray.fromObject(content);
		}
	}

	public static String getValue(String key) {
		initContent("db.conf");
		JSONObject obj = jsonArray.getJSONObject(0);
		return obj.getString(key);
	}
	
	private static String loadFile(String filePath) {
		BufferedReader reader = null;
		
		String content = "";
		try {
			FileInputStream iStream = new FileInputStream(filePath);
			InputStreamReader iStreamReader = new InputStreamReader( iStream, "UTF-8");
			reader = new BufferedReader(iStreamReader);
			String line = null;
			
			while((line = reader.readLine()) != null) {
				content += line;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return content;
	}
}
