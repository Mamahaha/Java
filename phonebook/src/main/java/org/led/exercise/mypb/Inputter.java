
// Package
package org.led.exercise.mypb;

// Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User commands & phone record attributes receiver
 * 
 */
public final class Inputter {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Noter noter = new Noter();
	public Inputter() {
	
	}
	
	/**
	 * read a string from console
	 * 
	 * @param title
	 *            string type that user needs to input. It might be a command or phone record attribute
	 * @return the input string
	 */
	public String getInput(String title) {
		String value = null;

		noter.show(">>Please input " + title + ": ");
		try {
			value = br.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}	
		
		return value;
	}
}
