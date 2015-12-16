package org.led.tools.cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
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
        generateMd5("aaaaaaaaaaaaaaaaaaaaaa");
    }
}
