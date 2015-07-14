package parallel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static String generateMd5(String input){
        final String MD5_STRING = "MD5";
        MessageDigest md = null;
        
        try {
			md = MessageDigest.getInstance(MD5_STRING);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        md.reset();

        byte[] hash = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        System.out.println("md5: " + sb.toString());
        return sb.toString();
    }
	
	public static void test() {
		for(;;) {
			Md5.generateMd5("aaaaaaaaaaaaaaaaaaaaaa");
			Md5.generateMd5("bbbbbbbbbbbbbbbbbbbbbb");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
