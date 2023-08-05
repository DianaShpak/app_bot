package core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class CommonJava {
    private static char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        int i = 0;
        int j = 0;
        while (i < l) {
            j += 1;
            out[j - 1] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            j += 1;
            out[j - 1] = DIGITS_LOWER[0x0F & data[i]];
            i += 1;
        }
        return out;
    }

}
