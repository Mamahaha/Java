package org.led.javacore.core1.chapter3;

import java.util.Arrays;

public class Chapter3 {
	private static void getCodePointCount() {
		String s = "Check code point count";
		int n = s.length();
		int cpCount = s.codePointCount(0, s.length());
		System.out.println("len: " + n + "; count: " + cpCount);
	}
	
	private static void print2DArray() {
		int a2d[][] = {
			{3,5,7,8},
			{8,3},
			{4,2,4},
			{9,5,2,7}
		};
		System.out.println(Arrays.deepToString(a2d));
	}
	
	public static void run() {
		//getCodePointCount();
		print2DArray();
	}
}
