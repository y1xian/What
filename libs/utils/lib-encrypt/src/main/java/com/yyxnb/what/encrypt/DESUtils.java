package com.yyxnb.what.encrypt;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESUtils {

    private final static String HEX = "0123456789ABCDEF";
    //DES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private final static String TRANSFORMATION = "DES/CBC/PKCS5Padding";
    //初始化向量参数，AES 为16bytes. DES 为8bytes.
    private final static String IVPARAMETERSPEC = "01020304";
    //DES是加密方式
    private final static String ALGORITHM = "DES";
    // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
    private static final String SHA1PRNG = "SHA1PRNG";

    // ------------------------------------- 静态 --------------------------------------------------

    /**
     * DES 加密
     *
     * @param message 原文
     * @param key     密钥,长度不能够小于8位
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptbyte = cipher.doFinal(message.getBytes());
        return new String(Base64.encode(encryptbyte, Base64.DEFAULT)).trim();
    }

    /**
     * DES解密
     *
     * @param message 密文
     * @param key     密钥,长度不能够小于8位
     * @return
     * @throws Exception
     */
    public static String desDecrypt(String message, String key) throws Exception {

        byte[] bytesrc = Base64.decode(message.getBytes(), Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }


    // ------------------------------------- 动态 --------------------------------------------------


    /*
     * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
     */
    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[20];
            localSecureRandom.nextBytes(bytes_key);
            String str_key = toHex(bytes_key);
            return str_key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 对密钥进行处理（方式一）
    @SuppressLint({"ObsoleteSdkInt", "DeletedProvider"})
    private static Key getRawKey(String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        //for android
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance(SHA1PRNG, "Crypto");
        } else {
            sr = SecureRandom.getInstance(SHA1PRNG);
        }
        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        sr.setSeed(key.getBytes());
        kgen.init(64, sr); //DES固定格式为64bits，即8bytes。
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return new SecretKeySpec(raw, ALGORITHM);
    }

    // 对密钥进行处理（方式二）
    private static Key getRawKey2(String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    //二进制转字符
    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String encode(String key, String data) {
        return encode(key, data.getBytes());
    }


    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String encode(String key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, getRawKey(key), iv);
            byte[] bytes = cipher.doFinal(data);
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取编码后的值
     *
     * @param key
     * @param data
     * @return
     */
    public static String decode(String key, String data) {
        return decode(key, Base64.decode(data, Base64.DEFAULT));
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     */
    public static String decode(String key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, getRawKey(key), iv);
            byte[] original = cipher.doFinal(data);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {

        try {
            String key = DESUtils.generateKey();
            String key2 = AESUtils.generateKey();
            String message = "风里来，雨里去";
            // DES 动态加密
            String encode = DESUtils.encode(key, message);
            // AES 加密
            String encrypt = AESUtils.encrypt(key2, encode);
            // AES 解密
            String decrypt = AESUtils.decrypt(key2, encrypt);
            // DES 解密
            String decode = DESUtils.decode(key, decrypt);

            System.out.println("message>>>" + message);
            System.out.println("DES加密>>>" + encode);
            System.out.println("AES加密>>>" + encode);
            System.out.println("AES解密>>>" + decode);
            System.out.println("DES解密>>>" + decode);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}