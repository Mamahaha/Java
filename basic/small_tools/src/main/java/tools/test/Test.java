package tools.test;

import java.util.HashSet;
import java.util.Set;

public class Test {
    private static Set<Ticker> initTickers(){
        Set<Ticker> keys = new HashSet<Ticker>();
        Ticker key1 = new Ticker(1,"000002");
        Ticker key2 = new Ticker(4,"000004");
        Ticker key3 = new Ticker(62,"603362");
        keys.add(key1);
        keys.add(key2);
        keys.add(key3);
        return keys;
    }

    public static void main(String[] args) {
        Set<Ticker> room1tickers = initTickers();
        String[] tickers = room1tickers.toArray(new String[] {});
    }
}
