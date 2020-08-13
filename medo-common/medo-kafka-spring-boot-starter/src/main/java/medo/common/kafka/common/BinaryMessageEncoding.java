package medo.common.kafka.common;

import java.nio.charset.Charset;

public class BinaryMessageEncoding {

    public static String bytesToString(byte[] bytes) {
        return new String(bytes, Charset.forName("UTF-8"));
    }

    public static byte[] stringToBytes(String string) {
        return string.getBytes(Charset.forName("UTF-8"));
    }

}
