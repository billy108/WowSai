package com.qianfeng.wowsai.tool;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/1
 */
public class AESUtils4 {
    static final String iv = "wowsai16wowsai16";

    public static String desEncrypt(String paramString1, String paramString2) {
        try {
            byte[] arrayOfByte = Base64.decode(paramString1, 2);
            Cipher localCipher = Cipher.getInstance("AES/CBC/NoPadding");
            localCipher.init(2, new SecretKeySpec(paramString2.getBytes("UTF-8"), "AES"), new IvParameterSpec("wowsai16wowsai16".getBytes("UTF-8")));
            String str = new String(localCipher.doFinal(arrayOfByte));
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String paramString1, String paramString2) {
        try {
            Cipher localCipher = Cipher.getInstance("AES/CBC/NoPadding");
            int i = localCipher.getBlockSize();
            byte[] arrayOfByte1 = paramString1.getBytes("UTF-8");
            int j = arrayOfByte1.length;
            if (j % i != 0)
                j += i - j % i;
            byte[] arrayOfByte2 = new byte[j];
            System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
            localCipher.init(1, new SecretKeySpec(paramString2.getBytes("UTF-8"), "AES"), new IvParameterSpec("wowsai16wowsai16".getBytes("UTF-8")));
            String str = Base64.encodeToString(localCipher.doFinal(arrayOfByte2), 2);
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public static String encryptDef(String paramString1, String paramString2) {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("saiwai");
        localStringBuffer.append(paramString2);
        localStringBuffer.append("saiwai");
        return encrypt(paramString1, localStringBuffer.toString().trim());
    }
}
