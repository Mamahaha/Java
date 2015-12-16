package org.led.tools.jvm.reflection;

import java.lang.reflect.Array;

/**
 * This is a tool to expand a fixed array with any element type using reflection
 * @author led
 *
 */
public class ArrayExpandor {
    public static Object expand(Object a) {
        if (a == null) {
            return null;
        }
        Class cl = a.getClass();
        if (!cl.isArray()) {
            return null;
        }
        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        int newLength = length * 11 /10 + 10;
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, length);
        return newArray;
    }
    
    public static void main(String[] args) {
        String[] ss = {"a", "bb", "ddd", "cccc"};
        String[] newSs = (String[]) expand(ss);
        System.out.println(ss.length + "; " + newSs.length);

    }
}
