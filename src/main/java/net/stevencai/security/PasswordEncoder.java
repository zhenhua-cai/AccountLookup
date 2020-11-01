package net.stevencai.security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    private static final String AES= "AES";
    private static final int keySize =128;

    private static String byteArrayToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for (byte aByte : bytes) {
            int v = aByte & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] hexStringToByteArray(String s){
        byte[] bytes = new byte[s.length()/2];
        for(int i = 0;i< bytes.length;i++){
            int index = i*2;
            int v = Integer.parseInt(s.substring(index,index+2),16);
            bytes[i] =(byte)v;
        }
        return bytes;
    }

    /**
     * Get random generated key
     * @return generated key
     * @throws NoSuchAlgorithmException
     */
    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(keySize);
        SecretKey sk = keyGenerator.generateKey();
        return byteArrayToHexString(sk.getEncoded());
    }

    /**
     * encrypt password with given key
     * @param password password to be encrypted
     * @return encrypted password.
     */
    public static String encryptPassword(String password,String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] byteKey = hexStringToByteArray(key);
        SecretKeySpec sks = new SecretKeySpec(byteKey,AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,sks, cipher.getParameters());
        return byteArrayToHexString(cipher.doFinal(password.getBytes()));
    }

    /**
     * decrypt password with given key
     * @param encryptedPassword encrypted password.
     * @param key public key.
     * @return decrypted password.
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decryptPassword(String encryptedPassword,String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] bytes = hexStringToByteArray(key);
        SecretKeySpec sks = new SecretKeySpec(bytes, AES);

        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,sks);

        byte[] decryptedPassword = cipher.doFinal(hexStringToByteArray(encryptedPassword));
        return new String(decryptedPassword);
    }

    public static int getKeySize() {
        return keySize;
    }
}
