package tools;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tools.cipher.AesCipher;

public class MyCipherTest {
    @Test
    public void testEncription() {
        assertEquals(
                AesCipher.encryptAES("embms1234"),
                "l6+Fg7onTNFKQV8KxKH8Rw==");
    }
    
    @Test
    public void testDecription() {
        assertEquals(
                AesCipher.decryptAES("l6+Fg7onTNFKQV8KxKH8Rw=="),
                "embms1234");
    }
}
