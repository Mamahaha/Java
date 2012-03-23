/*
 *             File : PhoneBookApp.java
 *        Classname : PhoneBookApp
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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Phone book application
 * 
 */
public final class PhoneBookApp extends Thread{

	private PhoneBook pb;
	private Inputter inputter;
	private Noter noter;
	private final String pbXml = "myphonebook.xml";
	private final String logCfg = "/logback.xml";
	private Logger logger;
	
	/**
	 * display all phone book operation commands
	 * 
	 * @param 
	 *            
	 * @return null
	 */
	private void showCmds() {
		System.out.println("1: Display all commands");
		System.out.println("2: Query records by name");
		System.out.println("3: Add a record");
		System.out.println("4: Edit a record by name");
		System.out.println("5: Remove records by name");
		System.out.println("0: Quit");
	}
	
	/**
	 * initialize logback 
	 * 
	 * @param 
	 *            
	 * @return true if initialization succeeded
	 */
	private boolean initLogger() {
		boolean result = true;
		
		logger = LoggerFactory.getLogger(PhoneBookApp.class);
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

		try {
			JoranConfigurator configurator = new JoranConfigurator();
			lc.reset();
			configurator.setContext(lc);
			configurator.doConfigure(PhoneBookApp.class
					.getResourceAsStream(logCfg));
		} catch (JoranException je) {
			noter.show("[Error]: Log system initialization failed.");
			result = false;
		}
		
		return result;
	}
	
	public PhoneBookApp() {
		inputter = new Inputter();
		pb = new PhoneBook();
		noter = new Noter();
	}
	
	/**
	 * main function of phone book application method  
	 * 
	 * @param 
	 *            
	 * @return null
	 */
	public void run() {
		if(!initLogger()) {
			return;
		}

		noter.show("\n<*************************************************>");
		noter.show("           Phonebook application started       ");
		noter.show("<*************************************************>");
		noter.show("[Note]: Input 1 to display all operation commands\n");
		
		logger.info("Phonebook application started");
		int cmd = 1;
		boolean result = false;
		
		boolean changeFlag = false;
		boolean stopFlag = false;
		List<PhoneRecord> subRecords = null;
		boolean initFlag = pb.xml2List(pbXml);
		if(!initFlag) {
			logger.error("Phonebook initialization failed. Please check the configuration and restart application");
		}
		
		while(initFlag) {
			result = false;
			try {
				String name = null;
				String number = null;
				String address = null;
				cmd = Integer.parseInt(inputter.getInput("command"));
				switch (cmd) {
				case 1 :
					noter.show("\n[Operation]: List all operation commands");
					logger.info("List all operation commands");
					showCmds();
					result = true;
					break;
				case 2 :
					subRecords.clear();
					noter.show("\n[Operation]: Query records by name. '*' represents all records");
					name = inputter.getInput("name");
					logger.info("Query records by name: " + name);
					subRecords = pb.queryByName(name);
					pb.displayRecords(subRecords);
					result = true;
					break;
				case 3 :
					noter.show("\n[Operation]: Add a record");
					name = inputter.getInput("name");
					number = inputter.getInput("number");
					address = inputter.getInput("address");
					logger.info("Add a record: " + name + " | " + number + " | " +address);
					result = pb.add(name, number, address);
					changeFlag = true;
					break;
				case 4 :
					noter.show("\n[Operation]: Edit a record by name");
					name = inputter.getInput("name");
					number = inputter.getInput("newNumber");
					address = inputter.getInput("newAddress");
					logger.info("Edit a record: " + name + " | " + number + " | " +address);
					result = pb.editByName(name, number, address);
					changeFlag = true;
					break;
				case 5 :
					noter.show("\n[Operation]: Remove records by name. '*' represents all records");
					name = inputter.getInput("name");
					logger.info("Remove a record with the name: " + name);
					result = pb.removeByName(name);
					changeFlag = true;
					break;
				case 0 :
					noter.show("\n[Operation]: Save and close phone book");
					logger.info("Save and close phone book");
					if(changeFlag) {
					  result = pb.list2Xml(pbXml);
					} else {
					  result = true;
					}
					stopFlag = true;
					break;
				default :
					logger.warn("Invalid command");
					noter.show("\n[Error]: Invalid command. Please input again.");
					break;				
				}
				
				if(result) {
					noter.show("[Result]: Operation succeeded\n");
					logger.info("Operation succeeded");
				} else {
					noter.show("[Result]: Operation failed\n");
					logger.warn("Operation failed");
				}
				
				if(stopFlag) {
					noter.show("<**********Phonebook application stopped**********>");
					break;
				}
			} catch (NumberFormatException e) {
				noter.show("\n[Error]: Invalid command. Please input again.");
				logger.warn("Invalid command");
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PhoneBookApp pbThread = new PhoneBookApp();
		
		pbThread.start();
	}

}
