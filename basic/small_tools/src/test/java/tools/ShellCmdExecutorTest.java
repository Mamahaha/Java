package tools;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ShellCmdExecutorTest {
    private ShellCmdExecutor sceInstance;
    
    @Before
    public void beforeTest() {
        sceInstance = ShellCmdExecutor.getInstance();
    }
    
    @Test
    public void testExecuteShellCmd() {
        try {
            assertEquals(sceInstance.executeCommand("date"), 0);
            System.out.println(sceInstance.getOutput());
        } catch (IOException e) {
            fail("Failed 1");
        } catch (InterruptedException e) {
            fail("Failed 2");
        }        
    }

}
