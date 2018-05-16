package tools.cipher;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import com.google.common.base.Strings;

public class RsaCipher {

    private String publicStr =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyfkLMHBzeq668bv4y4vNh9TTSWCC/P0SSQDXY\n"
            + "5dgWYIPdUNWI+DWMzgrPh3ktWFGk3GgwcIdkEbwHW6WqPoytbPX5CW73Gsnwo5SLGIUwdSB5zIU6\n"
            + "g1CH8JcetjiFXEGOJY96o+aLlhbJW7f/gAHRq1bQqD46TLyRVt163+VUgwIDAQAB";
    private String privateStr =
        "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALJ+QswcHN6rrrxu/jLi82H1NNJY\n"
            + "IL8/RJJANdjl2BZgg91Q1Yj4NYzOCs+HeS1YUaTcaDBwh2QRvAdbpao+jK1s9fkJbvcayfCjlIsY\n"
            + "hTB1IHnMhTqDUIfwlx62OIVcQY4lj3qj5ouWFslbt/+AAdGrVtCoPjpMvJFW3Xrf5VSDAgMBAAEC\n"
            + "gYBgR7kSTmvINMVpgVl+62qrQVA/0ie/4YnEdfxVxg1cbK1UeSDl5DVGgXHVZdcYfZZ0nitZTanK\n"
            + "ccKCGY5wGZNZpiOGHQYWBKkIuVdZU8UaAuz5AtqzRc6hN0+TjzNe/G56xjZO55TksYTA3ZdmLKuw\n"
            + "Jcu9FbBW2DSV7qKF9zO0cQJBAN86vNNSDtVmm9hry/PR6Wm/hXQXBia6/F8yZQKQoPdUeuX3Xp4J\n"
            + "flg5IGNBue+xXStcZdZMKpqiomulM7ig3OkCQQDMskaDJfWGbqeG4INzEQPilE45E4B16owyyjYD\n"
            + "hk3R3cPLjZ2OKQd9Ig4EqYtdmDxaD8nMdAuXzb//ncXRvRKLAkAdhIzXfLBNc6YD9i0f7o/o1dR1\n"
            + "x12e3Xblt6o3rpw6WEdwBUfTqfm0/MjVlylZEqD5TNyCe8veascDaPLv5QWRAkEAkZvDUul+pPkz\n"
            + "zFsKxqB07glnN2yq9bY5sqtrDsjLtJ0W+UYsrIJ9JENES4a7b4GqdLpEZ16pHHwsts1peZAQjwJB\n"
            + "AK4E22Oa9yH9FZZJsaHELW8cbiRIAoFPCUxejpv4uiRJAJw+abksu7J9Dit0Hn4AO64N4RXcqAKO\n"
            + "x5qqPihXH98=";

    private static final int BASE_BLOCK_SIZE = 128;
    private static final String KEY = "RSA";
    private static final String RSA_PKCS1 = "RSA/None/PKCS1Padding";
    private static final String KEY_STORE_TYPE = "PKCS12";

    private boolean initFlag = false;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher cipher;

    public void initWithString(String publicKeyStr, String privateKeyStr) {
        if (publicKeyStr != null) {
            loadPublicKey(publicKeyStr);
        }
        if (privateKeyStr != null) {
            loadPrivateKey(privateKeyStr);
        }

        initFlag = publicKey != null && privateKey != null;
    }

    public void initWithKeyStore(String keyStoreFilePath, String alias, String storePassword) {
        try {
            FileInputStream fis = new FileInputStream(keyStoreFilePath);
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
            keyStore.load(fis, storePassword.toCharArray());
            fis.close();

            Certificate certificate = keyStore.getCertificate(alias);
            publicKey = certificate.getPublicKey();
            //the keyPassword is the same as storePassword for PKCS12
            privateKey = (PrivateKey) keyStore.getKey(alias, storePassword.toCharArray());
            Security.addProvider(new BouncyCastleProvider());
            cipher = Cipher.getInstance(RSA_PKCS1, "BC");

            if (publicKey != null && privateKey != null) {
                initFlag = true;
            }
        } catch (Exception e) {
            System.out.println("failed to do load the keystore file: " + e);
        }
    }

    public String encrypt(String plainText) {
        if (Strings.isNullOrEmpty(plainText)) {
            return null;
        }

        byte[] cipherData = encrypt(plainText.getBytes());
        if (cipherData != null) {
            return byteArrayToBase64Str(cipherData);
        }
        return null;
    }

    public String decrypt(String cipherText) {
        if (Strings.isNullOrEmpty(cipherText)) {
            return null;
        }
        byte[] plainData = decrypt(base64StrToByteArray(cipherText));
        if (plainData != null) {
            return new String(plainData);
        }
        return null;
    }

    public boolean inited() {
        return initFlag;
    }

    private byte[] decrypt(byte[] cipherData) {
        if (cipherData == null || privateKey == null) {
            return null;
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            int blockSize = cipher.getBlockSize();
            int dataSize = cipherData.length;
            ByteArrayOutputStream plainData = new ByteArrayOutputStream(64);

            int offset = 0;
            for (int i = 0; dataSize - offset > 0; offset = i * blockSize) {
                if (dataSize - offset > blockSize) {
                    plainData.write(cipher.doFinal(cipherData, offset, blockSize));
                } else {
                    plainData.write(cipher.doFinal(cipherData, offset, dataSize - offset));
                }
                ++i;
            }

            return plainData.toByteArray();

        } catch (InvalidKeyException e) {
            System.out.println("解密私钥非法,请检查: " + e);
        } catch (IllegalBlockSizeException e) {
            System.out.println("密文长度非法: " + e);
        } catch (BadPaddingException e) {
            System.out.println("密文数据已损坏: " + e);
        } catch (IOException e) {
            System.out.println("密文数据已损坏: " + e);
        }

        return null;
    }

    private byte[] encrypt(byte[] plainData) {
        if (plainData == null || publicKey == null) {
            return null;
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(plainData.length);// 获得加密块加密后块大小
            int leavedSize = plainData.length % blockSize;
            int blocksSize = leavedSize != 0 ? plainData.length / blockSize + 1
                : plainData.length / blockSize;
            byte[] cipherData = new byte[outputSize * blocksSize];
            int i = 0;
            while (plainData.length - i * blockSize > 0) {
                if (plainData.length - i * blockSize > blockSize) {
                    cipher.doFinal(plainData, i * blockSize, blockSize, cipherData, i
                        * outputSize);
                } else {
                    cipher.doFinal(plainData, i * blockSize, plainData.length - i
                        * blockSize, cipherData, i * outputSize);
                }
                i++;
            }

            return cipherData;

        } catch (InvalidKeyException e) {
            System.out.println("加密公钥非法,请检查: " + e);
        } catch (IllegalBlockSizeException e) {
            System.out.println("明文长度非法: " + e);
        } catch (BadPaddingException e) {
            System.out.println("明文数据已损坏: " + e);
        } catch (ShortBufferException e) {
            System.out.println(e);
        }

        return null;
    }

    private void loadPublicKey(String publicKeyStr) {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("无此算法: " + e);
        } catch (InvalidKeySpecException e) {
            System.out.println("公钥非法: " + e);
        } catch (IOException e) {
            System.out.println("公钥数据内容读取错误: " + e);
        } catch (NullPointerException e) {
            System.out.println("公钥数据为空: " + e);
        }
    }

    private void loadPrivateKey(String privateKeyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY);
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            this.privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("无此算法: " + e);
        } catch (InvalidKeySpecException e) {
            System.out.println("私钥非法: " + e);
        } catch (IOException e) {
            System.out.println("私钥数据内容读取错误: " + e);
        } catch (NullPointerException e) {
            System.out.println("私钥数据为空: " + e);
        }
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

    private void testBytes(String oriData) {
        System.out
            .println("testBytes ori data len: " + oriData.length() + "; data: " + oriData);
        byte[] encData = encrypt(oriData.getBytes());
        System.out.println(
            "testBytes enc data len: " + encData.length + "; data: " + byteArrayToBase64Str(
                encData));
        byte[] decData = decrypt(encData);
        System.out.println(
            "testBytes dec data len: " + decData.length + "; data: " + new String(decData));
    }

    private void testString(String oriData) {
        System.out
            .println("testString ori data len: " + oriData.length() + "; data: " + oriData);
        String encData = encrypt(oriData);
        System.out
            .println("testString enc data len: " + encData.length() + "; data: " + encData);
        String decData = decrypt(encData);
        System.out
            .println("testString dec data len: " + decData.length() + "; data: " + decData);
    }

    private void testDecryptJS(String encData) {
        System.out
            .println("testDecryptJS enc data len: " + encData.length() + "; data: " + encData);
        String decData = decrypt(encData);
        System.out
            .println("testDecryptJS dec data len: " + decData.length() + "; data: " + decData);
    }

    //=======================================================================================
    public static void main(String[] args) {

        String oriData = "aI2p4TiG";
        String encData =
            "CfR57cWjCqhgcsMLSHavWhv8Ed0VU53KYfsbbOF7/3co7nb/AA93mWU8wHMZdZwJscMvVLSb"
                + "8t4ScPQj3F74tU/zV0qDYFzwIP1J/aFAMVo1fCSZUmn7Nbvir0DbxYWRDS9IQgtL/Q"
                + "3b1tEWm1baznlff6RCqoe2D9i2WNAtfu0=";
        RsaCipher cc = new RsaCipher();

        //init public & private key
        //cc.initWithString(publicStr, privateStr);
        cc.initWithKeyStore("/tmp/rsa/pkcs12/smartp12.keystore", "smart", "1qaz@WSX3edc");

        long now = System.currentTimeMillis();
        //cc.testBytes(oriData);
        //cc.testString(oriData);
        cc.testDecryptJS(encData);
        System.out.println("test cost: " + (System.currentTimeMillis() - now));
    }
}
