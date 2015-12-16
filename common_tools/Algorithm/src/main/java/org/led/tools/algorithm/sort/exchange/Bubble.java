package org.led.tools.algorithm.sort.exchange;

import org.led.tools.algorithm.sort.BaseSort;

public class Bubble extends BaseSort {

    public static void sort(int[] rawData) {
        int idx = rawData.length;
        while (idx > 1) {
            for (int i = 0; i < idx-1; i++) {
                cas(rawData, i, i+1);
            }
            idx--;
        }
        showResult(rawData, "bub: ");
    }
}
