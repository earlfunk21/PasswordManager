package com.example.passwordmanager.util;

import android.os.Build;

import com.example.passwordmanager.BuildConfig;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Security {
    public static final String SECRET_KEY = BuildConfig.AES_SECRET_KEY;

    public String encrypt(String strNormalText) {
        String normalTextEnc = "";
        try {
            normalTextEnc = AESHelper.encrypt(SECRET_KEY, strNormalText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalTextEnc;
    }

    public String decrypt(String strEncryptedText) {
        String strDecryptedText = "";
        try {
            strDecryptedText = AESHelper.decrypt(SECRET_KEY, strEncryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDecryptedText;
    }


}