package tools;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tools.cipher.AesCipher;

public class MyCipherTest {
    @Test
    public void testEncription() {

    }
    
    @Test
    public void testDecription() {
        assertEquals(
                AesCipher.decrypt("l6+Fg7onTNFKQV8KxKH8Rw=="),
                "embms1234");
    }
}
