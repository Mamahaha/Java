package org.led.tools.algorithm.sort;

import org.led.tools.algorithm.sort.BaseSort;
import org.led.tools.algorithm.sort.exchange.Bubble;
import org.led.tools.algorithm.sort.exchange.Quick;
import org.led.tools.algorithm.sort.insert.Insert;
import org.led.tools.algorithm.sort.insert.Shell;
import org.led.tools.algorithm.sort.select.Select;
public class SortApp {
    private static int[] rawData = {16,123,54,321,12,42,6765,322,123,3,64,54,232,454,
        32,7878,3423,642,234,543,55,1,280,402,22,43,675,384,5953,29034,592,651,5893,9034,233,646,231};
    
   
    public static void main(String[] args) {
        BaseSort.showResult(rawData, "ori: ");
        //long start = System.currentTimeMillis();
        //Bubble.sort(rawData);
        //Shell.sort(rawData);
        //Select.sort(rawData);
        Insert.sort(rawData);
        //Quick.sort(rawData);
        //long end = System.currentTimeMillis();
        //System.out.println("Time cost: " + (end - start));
    }

}
