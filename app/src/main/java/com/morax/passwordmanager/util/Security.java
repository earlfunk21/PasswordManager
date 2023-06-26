package com.morax.passwordmanager.util;

import com.morax.passwordmanager.BuildConfig;

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