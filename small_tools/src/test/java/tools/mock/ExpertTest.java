package tools.mock;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class ExpertTest {

    @Test
    public void testVerify1() {
        // From the printing result we can find that all real functions in
        // Advance and Base were not executed.
        Advance mockAdvance = mock(Advance.class);

        mockAdvance.getBaseI();
        verify(mockAdvance).getBaseI(); // yes it's executed
        // verify(mockAdvance).getBase(); // no it's not executed
    }
    
    @Test
    public void testVerify2() {
        // Used to show how powerful 'verify' is
        Advance mockAdvance = mock(Advance.class);
        Expert ex = new Expert(mockAdvance);
        
        // stubbing
        when(mockAdvance.getBaseI()).thenReturn(3); // 0
        assertEquals(ex.getBaseI(), 3);  // 2! Strange, right? The answer lies in the implementation of ex.getBaseI()
        
        verify(mockAdvance, times(2)).getBaseI();
    }
    
    @Test
    public void testVerify3() {
        // Used to show how powerful 'verify' is
        Advance mockAdvance = mock(Advance.class);
        
        when(mockAdvance.getIndex(anyInt())).thenReturn(3);  // 0
        mockAdvance.getIndex(0); // 1
        mockAdvance.getIndex(4); // 2
        
        verify(mockAdvance, times(2)).getIndex(anyInt());
        verify(mockAdvance, never()).getBaseI();
        verify(mockAdvance, atLeastOnce()).getIndex(anyInt());
        verify(mockAdvance, atLeast(2)).getIndex(anyInt());
        verify(mockAdvance, atMost(2)).getIndex(anyInt());
    }    

    @Test
    public void testStub() {
        // From the printing result we can find how to create a faked object
        // with expected return value.
        Advance mockAdvance = mock(Advance.class);
        Expert ex = new Expert(mockAdvance);

        // stubbing
        when(mockAdvance.getBaseI()).thenReturn(3);
        assertEquals(ex.getBaseI(), 3);
    }

    @Test
    public void testParameter() {
        // Used to check if the mocked function(use when-then) is really executed.
        Advance mockAdvance = mock(Advance.class);
        
        when(mockAdvance.getIndex(anyInt())).thenReturn(3);
        assertEquals(mockAdvance.getIndex(0), 3); // real function will return 10 
        assertEquals(mockAdvance.getIndex(4), 3); // real function will return 4
    }
    
    
}
