package tools;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MyCipherTest {
    @Test
    public void testEncription() {
        assertEquals(
                CommonCipher.encryptAES("embms1234"),
                "l6+Fg7onTNFKQV8KxKH8Rw==");
    }
    
    @Test
    public void testDecription() {
        assertEquals(
                CommonCipher.decryptAES("l6+Fg7onTNFKQV8KxKH8Rw=="),
                "embms1234");
    }
}
