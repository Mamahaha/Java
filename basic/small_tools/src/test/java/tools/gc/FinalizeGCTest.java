package tools.gc;

import static org.junit.Assert.*;

import org.junit.Test;
import tools.jvm.FinalizeGC;

public class FinalizeGCTest {

    @Test
    public void testRun() {
        try {
            FinalizeGC.run();
            assert(true);
        } catch (Exception e) {
            fail("Failed");
        }
    }

}
