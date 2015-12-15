package org.led.tools.Algorithm.sort;

public class BaseSort {
    public static void cas(int[] rawData, int i, int j) {
        if (rawData[i] > rawData[j]) {
            swap(rawData, i, j);
        }
    }
    
    public static void swap(int[] rawData, int i, int j) {
        int temp = rawData[i];
        rawData[i] = rawData[j];
        rawData[j] = temp;
    }
    
    public static void showResult(int[] rawData, String prefix) {
        String result = prefix;
        for (int i : rawData) {
            result += i + ", ";
        }
        
        System.out.println(result.substring(0, result.length()-2));
    }
    
    public static void sort(int[] rawData) {}
}
