package org.led.tools.Algorithm.sort;

import org.led.tools.Algorithm.sort.exchange.Bubble;

public class SortApp {
    private static int[] rawData = {16, 123,54,321,12,42,6765,322,123,3,64,54,232,454,32,7878,3423,642,234,543,55,1};
    
    private static void showResult(String prefix) {
        String result = prefix;
        for (int i : rawData) {
            result += i + ", ";
        }
        
        System.out.println(result.substring(0, result.length()-2));
    }
    public static void main(String[] args) {
        showResult("b: ");
        long start = System.currentTimeMillis();
        Bubble.sort(rawData);
        long end = System.currentTimeMillis();
        showResult("a: ");
        System.out.println("Time cost: " + (end - start));

    }

}
