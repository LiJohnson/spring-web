package net.inno.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-29
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public class Md5Service {
    /**
     * 加密
     */
    public static String encString(String pass, String key) {
        if (pass == null || key == null) {
            return null;
        }

        String result = null;
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte[] byteMd5key = md.digest();
            byte[] bytePass = pass.getBytes();

            if (bytePass.length <= byteMd5key.length) {
                byte[] byteResult = new byte[bytePass.length];
                for (int i = 0; i < bytePass.length; i++) {
                    byteResult[i] = new Integer(bytePass[i] ^ byteMd5key[i]).byteValue();
                }
                result = byte2hex(byteResult);
            } else {
                int len = byteMd5key.length * (bytePass.length / byteMd5key.length + 1);
                byte[] byteMd5KeyA = new byte[len];
                for (int i = 0; i < len; i += byteMd5key.length) {
                    System.arraycopy(byteMd5key, 0, byteMd5KeyA, i, byteMd5key.length);
                }
                byte[] byteResult = new byte[bytePass.length];
                for (int i = 0; i < bytePass.length; i++) {
                    byteResult[i] = new Integer(bytePass[i] ^ byteMd5KeyA[i]).byteValue();
                }
                result = byte2hex(byteResult);
            }
        }
        catch (NoSuchAlgorithmException e) {
            result = null;
        }

        return result;
    }

    /**
     * 解密
     */
    public static String decString(String src, String key) {
        if (src == null || key == null) {
            return null;
        }

        String pass = null;
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte[] byteMd5key = md.digest();
            byte[] byteSrc = hex2byte(src);

            if (byteSrc.length <= byteMd5key.length) {
                byte[] byteResult = new byte[byteSrc.length];
                for (int i = 0; i < byteSrc.length; i++) {
                    byteResult[i] = new Integer(byteSrc[i] ^ byteMd5key[i]).byteValue();
                }
                pass = new String(byteResult);
            } else {
                byte[] byteMd5KeyA = new byte[byteSrc.length + byteMd5key.length];
                for (int i = 0; i < byteSrc.length; i += byteMd5key.length) {
                    System.arraycopy(byteMd5key, 0, byteMd5KeyA, i, byteMd5key.length);
                }
                byte[] byteResult = new byte[byteSrc.length];
                for (int i = 0; i < byteSrc.length; i++) {
                    byteResult[i] = new Integer(byteSrc[i] ^ byteMd5KeyA[i]).byteValue();
                }
                pass = new String(byteResult);
            }
        }
        catch (NoSuchAlgorithmException e) {
            pass = null;
        }

        return pass;
    }

    /**
     * byte转二进制字符串
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    /**
     * 二进制字符串转byte
     */
    public static byte[] hex2byte(String s) {
        byte[] hs = new byte[s.length() / 2];
        for (int i = 0, j = 0; i < s.length(); i += 2, j++) {
            String s1 = s.substring(i, i + 2);
            hs[j] = Integer.valueOf(s1, 16).byteValue();
        }
        return hs;
    }
}
