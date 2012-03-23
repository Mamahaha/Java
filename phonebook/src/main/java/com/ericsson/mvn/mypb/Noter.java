/*
 *             File : Noter.java
 *        Classname : Noter
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


/**
 * display operation status or phone record attribute
 * 
 */
public final class Noter {
	public Noter() {
		
	}
	
	/**
	 * show operation status or phone record attribute to user
	 * 
	 * @param info
	 *           infomation to be shown
	 * @return null
	 */
	public void show(String info) {
		System.out.println(info);
	}
}
