package tools.cipher;

import org.apache.commons.codec.binary.Base64;

public final class TranscodeUtil {

    static final String GENERATE_AES_KEY_FAILED = "generateAESKeyFailed";

    private TranscodeUtil() {
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

}
