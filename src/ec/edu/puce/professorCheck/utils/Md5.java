/* 
 * Md5.java
 * Sep 11, 2009 
 * Copyright 2009 Saviasoft Cia. Ltda. 
 */
package ec.edu.puce.professorCheck.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Calse para hashear una cadena de texto en md5 (hex)
 *
 * @author Juan Ochoa
 *
 */
public class Md5 {

    public static String hash(String stringToHash)
            throws NoSuchAlgorithmException {

        MessageDigest m;
        m = MessageDigest.getInstance("MD5");
        byte messageDigest[] = m.digest(stringToHash.getBytes());

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xFF & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();

    }
}