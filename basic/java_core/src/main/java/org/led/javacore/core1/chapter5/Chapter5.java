package org.led.javacore.core1.chapter5;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Chapter5 {
	
	public static void testEnum() {
		Event e = Event.START;
		System.out.println(e + "; " + e.toString() + "; " + e.getAbbrev() + "; " + e.getIndex());
		
		Event e2 = Event.valueOf("TERMINATE");
		System.out.println(e2 + "; " + e2.toString() + "; " + e2.getAbbrev() + "; " + e2.getIndex());
	}
	
	public static void testReflection() {
		//test 1
		Viecle o1 = new Bicycle();
		Class c1 = o1.getClass();
		System.out.println("c1 class: " + c1 + "; " + Viecle.class + "; " + c1.getComponentType());
		
		String s2 = "org.led.javacore.core1.chapter5.Car";
		
		//test 2: constructor without parameter		
		try {
			Class c2 = Class.forName(s2);
			Car o2 = (Car) c2.newInstance();
			//System.out.print(o2.getClass());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		//test 3: constructor with parameter
		try {
			Class c3  = Class.forName(s2);
			Class[] paramTypes = {String.class, String.class};
			Object[] params = {"NBengine", "SBtype"};
			Constructor con = c3.getConstructor(paramTypes);
			Car obj = (Car)con.newInstance(params);
			//System.out.println(obj.getEngine() + "; " + obj.getType());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//test 4: call method with parameter
		String methodName = "setType";
		try {
			Class c4 = Class.forName(s2);
			Car obj = (Car)c4.newInstance();
			Class[] paramTypes = {String.class};
			Method method = c4.getMethod(methodName, paramTypes);			
			Object[] params = {"SBtype2"};
			method.invoke(obj, params);
			//System.out.println(obj.getType());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//test 5: get private attribute of super class, and change its value
		String attrName = "state";
		try {
			Class c5 = Class.forName(s2);
			Car obj = (Car)c5.newInstance();
			Field f5 = c5.getSuperclass().getDeclaredField(attrName);
			f5.setAccessible(true);
			f5.set(obj, Viecle.State.RUNNING);
			//System.out.println(f5.get(obj));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static void run() {
		//testEnum();
		testReflection();
	}
}
