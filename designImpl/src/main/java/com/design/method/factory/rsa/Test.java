package com.design.method.factory.rsa;



import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
public class Test {


    private static final String CHART_SET = "utf-8";

    /** 指定加密算法为RSA */
    private static final String ALGORITHM = "RSA";
    /** 密钥长度，用来初始化 */
    private static final int KEYSIZE = 2048;

    //私钥文件路径(RSAUtil是RSA工具类的类名)
    public static final String PRIVATE_KEY_FILE = "privatekey.key";
    //公钥文件路径
    public static final String PUBLIC_KEY_FILE = "publickey.key";

    public static void main(String[] args) throws Exception{
//        createSRAFile();
        System.out.println(encrypt("123444"));
    }

    /**
     *
     * @throws Exception
     * @Desc create public and private key
     */
   private static void createSRAFile() throws Exception{
       KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
       keyGen.initialize(KEYSIZE);
       KeyPair pair = keyGen.generateKeyPair();
       String publicKey = base64Encode(getPublicKey(pair));
       String privateKey = base64Encode(getPrivateKey(pair));
       createSRAFile(PUBLIC_KEY_FILE, publicKey);
       createSRAFile(PRIVATE_KEY_FILE,privateKey);

   }

    private static void createSRAFile(String fileName, String key) throws Exception{
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }else {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(key.getBytes("UTF-8"));
        fos.flush();
        fos.close();
    }

    private static byte[] getPrivateKey(KeyPair pair ) {
        return pair.getPublic().getEncoded();
   }

   private static byte[] getPublicKey(KeyPair pair) {
        return pair.getPrivate().getEncoded();
   }

   public static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64Decode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static byte[] loadKey(File file) throws Exception{
        FileInputStream fis = new FileInputStream(file);
        byte[] keyBytes = new byte[(int)file.length()];
        int offset = 0;
        int numRead = 0;
        while (offset < keyBytes.length
                && (numRead = fis.read(keyBytes, offset, keyBytes.length - offset)) >= 0) {
            offset += numRead;
        }
        fis.close();
        return keyBytes;
    }

    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt(String str) throws Exception{
        //base64编码的公钥
        byte[] decoded = base64Decode(loadKey(new File(PUBLIC_KEY_FILE)));
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = cipher.doFinal(str.getBytes(CHART_SET)).toString();
        return outStr;

    }



}
