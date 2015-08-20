package tools;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CommonCipher {
    private static final int KEY_LENGTH = 128;
    private static final String KEY_AES = "AES";
    private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
    private static final String MY_KEY = "shTVEmbmsflowers";

    public static String encryptAES(String data) {
        String realKey = generateAESKey(KEY_LENGTH, MY_KEY);
        return cipherAES(data, realKey, Cipher.ENCRYPT_MODE);
    }

    public static String decryptAES(String data) {
        String realKey = generateAESKey(KEY_LENGTH, MY_KEY);
        return cipherAES(data, realKey, Cipher.DECRYPT_MODE);
    }
    
    private static String strToBase64Str(String str) {
        return byteArrayToBase64Str(str.getBytes());
    }

    private static String base64StrToStr(String base64Str) {
        return new String(Base64.decodeBase64(base64Str));
    }

    private static String byteArrayToBase64Str(byte byteArray[]) {
        return new String(Base64.encodeBase64(byteArray));
    }

    private static byte[] base64StrToByteArray(String base64Str) {
        return Base64.decodeBase64(base64Str);
    }

    private static String generateAESKey(int keySize, String seed) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed.getBytes());
            kgen.init(keySize, secureRandom);
            SecretKey key = kgen.generateKey();
            return byteArrayToBase64Str(key.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static String cipherAES(String data, String key, int mode) {
        try {
            Key k = toKey(key, KEY_AES);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(
                    k.getEncoded());
            Cipher ecipher = Cipher.getInstance(ALGORITHM_AES);
            ecipher.init(mode, k, paramSpec);
            return mode == Cipher.DECRYPT_MODE ? new String(
                    ecipher.doFinal(base64StrToByteArray(data)))
                    : byteArrayToBase64Str(ecipher.doFinal(data
                            .getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Key toKey(String key, String algorithm) {
        return new SecretKeySpec(base64StrToByteArray(key),
                algorithm);
    }
}
