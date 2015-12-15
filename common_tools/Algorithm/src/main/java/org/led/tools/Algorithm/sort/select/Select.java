package org.led.tools.Algorithm.sort.select;

import org.led.tools.Algorithm.sort.BaseSort;

public class Select extends BaseSort {
    private static int largest(int[]rawData, int start, int stop) {
        int max = rawData[start];
        int idx = start;
        for (int i=start; i<=stop; i++) {
            if (max < rawData[i]) {
                max = rawData[i];
                idx = i;
            }
        }
        return idx;
    }
    
    public static void sort(int[] rawData) {
        int len = rawData.length;
        for (int i=len-1; i>0 ; i--) {
            int idx = largest(rawData, 0, i);
            swap(rawData, idx, i);
        }
        showResult(rawData, "sel: ");
    }
}
