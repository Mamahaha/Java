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

public class NewAesCipher {
    private static final int KEY_LENGTH = 128;
    private static final String SEED = "manchesterunited";
    private static final String KEY_AES = "AES";
    private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
    public static final int AES_IV_LENGTH = 16;

    public NewAesCipher() {
    }

    public static void main(String[] args) {
        NewAesCipher a = new NewAesCipher();
        System.out.println(a.encrypt("123123"));
        System.out.println(a.decrypt("4arSzzL3dzx2NhdF8EUoJA==cdAntkHCZbN+EyLTMdCuhg=="));
    }

    public String decrypt(String encryptionText) {
        byte[] randomIV = Arrays.copyOf(TranscodeUtil.base64StrToByteArray(encryptionText), 16);
        String encryptionRealText = encryptionText
            .substring(TranscodeUtil.byteArrayToBase64Str(randomIV).length());
        return decryptAES(encryptionRealText, generateAESKey(KEY_LENGTH, SEED),
            new IvParameterSpec(randomIV, 0, AES_IV_LENGTH));
    }

    public String encrypt(String plaintText) {
        byte[] randomIV = genereteRandomIV();
        return TranscodeUtil.byteArrayToBase64Str(randomIV) + encryptAES(plaintText,
            generateAESKey(KEY_LENGTH, SEED), new IvParameterSpec(randomIV, 0, AES_IV_LENGTH));
    }

    private String generateAESKey(int keySize, String seed) {
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
        return TranscodeUtil.byteArrayToBase64Str(key.getEncoded());
    }


    private String encryptAES(String data, String key, AlgorithmParameterSpec paramSpec) {
        return cipherAES(data, key, Cipher.ENCRYPT_MODE, paramSpec);
    }

    private String decryptAES(String data, String key, AlgorithmParameterSpec paramSpec) {
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


    private String cipherAES(String data, String key, int mode, AlgorithmParameterSpec paramSpec) {
        Key k = toKey(key, KEY_AES);
        if (paramSpec == null) {
            paramSpec = new IvParameterSpec(k.getEncoded(), 0, AES_IV_LENGTH);
        }
        try {
            Cipher ecipher = Cipher.getInstance(ALGORITHM_AES);
            ecipher.init(mode, k, paramSpec);
            return mode == Cipher.DECRYPT_MODE ? new String(ecipher.doFinal(TranscodeUtil
                .base64StrToByteArray(data))) : TranscodeUtil.byteArrayToBase64Str(ecipher
                .doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Key toKey(String key, String algorithm) {
        return new SecretKeySpec(TranscodeUtil.base64StrToByteArray(key), algorithm);
    }
}
