package org.led.tools.BmcDbOperator.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigLoader {

	private static JSONObject jsonObj = null;
	private static String confPath = "/var/postgres/conf/db.conf";
	
	public static void initContent() {
		if (jsonObj == null) {
		    loadFile2();
		}
	}

	public static String getValue(String key) {
        initContent(); 
        if (jsonObj.containsKey(key)) {
            String value = (String) jsonObj.get(key);
            return value;
        } else {
            return null;
        }
    }
	
	private static void loadFile2() {
	    JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(confPath));
            jsonObj = (JSONObject) obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private static String loadFile() {
		BufferedReader reader = null;
		
		String content = "";
		try {
			FileInputStream iStream = new FileInputStream(confPath);
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
