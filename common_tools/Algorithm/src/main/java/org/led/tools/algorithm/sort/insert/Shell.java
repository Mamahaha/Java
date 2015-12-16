package org.led.tools.algorithm.sort.insert;

import org.led.tools.algorithm.sort.BaseSort;

public class Shell extends BaseSort {
    private static int[] gaps = {5,2,1};
    
    public static void sort(int[] rawData) {
        for(int gap : gaps) {
            int idx = rawData.length;
            while (idx > 1) {
                for (int i=0; i<idx-gap-1; i+=gap) {
                    cas(rawData, i, i+gap);
                }
                idx--;
            }
        }
        showResult(rawData, "shl: ");
    }

}
