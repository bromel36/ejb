package com.bromel.ejb.utils;


import com.password4j.Hash;
import com.password4j.Password;

public class PasswordUtil {
    public static String hashPassword(String plainText) {
        return Password.hash(plainText)
                .withArgon2()
                .getResult();

    }

    public static boolean checkPassword(String plainText, String hashed) {
        return Password.check(plainText, hashed).withArgon2();
    }
}
