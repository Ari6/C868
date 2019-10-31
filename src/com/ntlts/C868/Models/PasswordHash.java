package com.ntlts.C868.Models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHash {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATION_COUNT = 10000;
    private static final int KEY_LENGTH = 256;

    public static String getHashedPassword(String password, String salt) {
        char[] passCharAry = password.toCharArray();
        byte[] hashedSalt = getSalt(salt);

        //PBE key
        PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);
        //For secret key
        SecretKeyFactory skf = null;

        try {
            skf = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        SecretKey secretKey = null;
        try {
            secretKey = skf.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        byte[] passByteAry = secretKey.getEncoded();

        StringBuffer sf = new StringBuffer(64);
        for (byte b : passByteAry) {
            sf.append(String.format("%02x", b & 0xff));
        }
        return sf.toString();
    }

    private static byte[] getSalt(String salt) {
        byte[] saltBytes = null;
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(messageDigest != null) {
            messageDigest.update(salt.getBytes());
            saltBytes = messageDigest.digest();
        }
        return saltBytes;
    }

}
