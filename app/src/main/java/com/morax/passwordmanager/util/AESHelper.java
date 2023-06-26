package com.morax.passwordmanager.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESHelper {
    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 1000;
    private static final int KEY_LENGTH = 256;

    public static String encrypt(String password, String data) throws Exception {
        byte[] salt = generateSalt();
        SecretKey secretKey = generateSecretKey(password.toCharArray(), salt);
        byte[] iv = generateInitializationVector();
        byte[] encryptedData = encrypt(secretKey, iv, data.getBytes(StandardCharsets.UTF_8));
        byte[] combined = new byte[salt.length + iv.length + encryptedData.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(encryptedData, 0, combined, salt.length + iv.length, encryptedData.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    public static String decrypt(String password, String encryptedData) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        byte[] salt = Arrays.copyOfRange(combined, 0, 16);
        byte[] iv = Arrays.copyOfRange(combined, 16, 32);
        byte[] encryptedBytes = Arrays.copyOfRange(combined, 32, combined.length);
        SecretKey secretKey = generateSecretKey(password.toCharArray(), salt);
        byte[] decryptedBytes = decrypt(secretKey, iv, encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        // Generate random salt bytes
        // You can use a secure random number generator for production use
        return salt;
    }

    private static byte[] generateInitializationVector() {
        byte[] iv = new byte[16];
        // Generate random IV bytes
        // You can use a secure random number generator for production use
        return iv;
    }

    private static SecretKey generateSecretKey(char[] password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKey secretKey = factory.generateSecret(spec);
        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }

    private static byte[] encrypt(SecretKey secretKey, byte[] iv, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(SecretKey secretKey, byte[] iv, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(encryptedData);
    }
}
