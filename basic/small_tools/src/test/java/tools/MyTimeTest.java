package tools;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class MyTimeTest {
    
    @Test
    public void testNtp2Utc() {

    }

    @Test
    public void testGenerateDate() {
        try {
            assertEquals(
                    MyTime.generateUtcDate("20150421024523", "yyyyMMddHHmmss"),
                    1429584323);
        } catch (ParseException e) {
            fail("Parsing error");
        }
    }

}
