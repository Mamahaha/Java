package org.led.tools.Algorithm.sort.exchange;

import org.led.tools.Algorithm.sort.BaseSort;

public class Quick extends BaseSort{
    private static void quickSort(int[] rawData, int start, int stop) {
        if (start >= stop) {
            return;
        }
        
        int value = rawData[start];
        int idx1 = start+1;
        int idx2 = stop;
        while (idx1 < idx2) {
            while ((rawData[idx1] < value) && (idx1 < idx2)) {
                idx1++;
            }
            while ((rawData[idx2] >= value) && (idx1 < idx2)) {
                idx2--;
            }
            swap(rawData, idx1, idx2);
            
        }
        if(rawData[idx1] < rawData[start]) {
            swap(rawData, start, idx1);
        } else {
            idx2--;
        }
        
        
        quickSort(rawData, start, idx1-1);
        quickSort(rawData, idx2+1, stop);
    }
    public static void sort(int[] rawData) {
        quickSort(rawData, 0, rawData.length-1);
        showResult(rawData, "qck: ");
    }
}
