package org.led.tools.Algorithm.sort.insert;

import org.led.tools.Algorithm.sort.BaseSort;

public class Insert extends BaseSort{
    private static int findPos(int[]rawData, int start, int stop, int value) {
        //suppose array[start, stop] is already sorted
        int idx = stop+1;
        while ((idx> start) && (rawData[idx-1] > value)) {
            idx--;
        }
        return idx;
    }
    
    private static void movePos(int[]rawData, int start, int stop, int value) {
        for (int i=stop; i>=start; i--) {
            rawData[i+1] = rawData[i];
        }
        rawData[start] = value;
    }
    
    public static void sort(int[] rawData) {
        int len = rawData.length;
        for (int i=0; i<len-1; i++) {
            int value = rawData[i+1];
            int idx = findPos(rawData, 0, i, value);
            movePos(rawData, idx, i, value);
        }
        showResult(rawData, "ins: ");
    }
}
