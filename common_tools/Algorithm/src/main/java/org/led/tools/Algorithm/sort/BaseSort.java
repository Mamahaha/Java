package org.led.tools.Algorithm.sort;

public class BaseSort {
    public static void cas(int[] rawData, int i, int j) {
        if (rawData[i] > rawData[j]) {
            int temp = rawData[i];
            rawData[i] = rawData[j];
            rawData[j] = temp;
        }
    }
    
    public static void sort(int[] rawData) {}
}
