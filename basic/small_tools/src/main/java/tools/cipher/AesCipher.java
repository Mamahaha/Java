package tools.cipher;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AesCipher {
    private static final int KEY_LENGTH = 128;
    private static final String SEED = "manchesterunited";
    private static final String KEY_AES = "AES";
    private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
    public static final int AES_IV_LENGTH = 16;

    public AesCipher() {
    }


    public static String decrypt(String encryptionText) {
        byte[] randomIV = Arrays.copyOf( base64StrToByteArray(encryptionText), 16);
        String encryptionRealText = encryptionText
            .substring( byteArrayToBase64Str(randomIV).length());
        return decryptAES(encryptionRealText, generateAESKey(KEY_LENGTH, SEED),
            new IvParameterSpec(randomIV, 0, AES_IV_LENGTH));
    }

    public String encrypt(String plaintText) {
        byte[] randomIV = genereteRandomIV();
        return  byteArrayToBase64Str(randomIV) + encryptAES(plaintText,
            generateAESKey(KEY_LENGTH, SEED), new IvParameterSpec(randomIV, 0, AES_IV_LENGTH));
    }

    private static String generateAESKey(int keySize, String seed) {
        KeyGenerator kgen;
        SecureRandom secureRandom;

        try {
            kgen = KeyGenerator.getInstance(KEY_AES);
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        secureRandom.setSeed(seed.getBytes());
        kgen.init(keySize, secureRandom);
        SecretKey key = kgen.generateKey();
        return  byteArrayToBase64Str(key.getEncoded());
    }


    private String encryptAES(String data, String key, AlgorithmParameterSpec paramSpec) {
        return cipherAES(data, key, Cipher.ENCRYPT_MODE, paramSpec);
    }

    private static String decryptAES(String data, String key, AlgorithmParameterSpec paramSpec) {
        return cipherAES(data, key, Cipher.DECRYPT_MODE, paramSpec);
    }


    /**
     * Generates a random IV to be used in the encryption process
     *
     * @return The IV's byte representation
     */
    private byte[] genereteRandomIV() {
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[16];
        random.nextBytes(ivBytes);
        return ivBytes;
    }


    private static String cipherAES(String data, String key, int mode, AlgorithmParameterSpec paramSpec) {
        Key k = toKey(key, KEY_AES);
        if (paramSpec == null) {
            paramSpec = new IvParameterSpec(k.getEncoded(), 0, AES_IV_LENGTH);
        }
        try {
            Cipher ecipher = Cipher.getInstance(ALGORITHM_AES);
            ecipher.init(mode, k, paramSpec);
            return mode == Cipher.DECRYPT_MODE ? new String(ecipher.doFinal(base64StrToByteArray(data))) :  byteArrayToBase64Str(ecipher
                .doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static Key toKey(String key, String algorithm) {
        return new SecretKeySpec( base64StrToByteArray(key), algorithm);
    }

    public static String strToBase64Str(String str) {
        return byteArrayToBase64Str(str.getBytes());
    }

    public static String base64StrToStr(String base64Str) {
        return new String(Base64.decodeBase64(base64Str));
    }

    public static String byteArrayToBase64Str(byte byteArray[]) {
        return new String(Base64.encodeBase64(byteArray));
    }

    public static byte[] base64StrToByteArray(String base64Str) {
        return Base64.decodeBase64(base64Str);
    }
    
    public static void main(String[] args) {
        System.out.println(decrypt("fc7WotKJeDOODrk+apukJg==8CsHHzIur3PrGiYlQxAgGg=="));
    }
}
