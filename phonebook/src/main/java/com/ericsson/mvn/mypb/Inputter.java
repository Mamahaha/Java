/*
 *             File : Inputter.java
 *        Classname : Inputter
 *        Author(s) : Led Xu
 *          Created : 2012-3-23
 * 
 * Copyright (c) 2012 Ericsson AB, Sweden.
 * All rights reserved.
 * The Copyright to the computer program(s) herein is the property of
 * Ericsson AB, Sweden.
 * The program(s) may be used and/or copied with the written permission
 * from Ericsson AB or in accordance with the terms
 * and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 */

// Package
package com.ericsson.mvn.mypb;

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
