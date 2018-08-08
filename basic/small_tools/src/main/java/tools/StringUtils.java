package tools;

import java.util.Collections;

public class StringUtils {

    private static String repeatAndJoin(String a, int count) {
        return String.join("", Collections.nCopies(count, a));
    }

    public static void main(String[] args) {
        System.out.println(repeatAndJoin("#", 6));
    }
}
