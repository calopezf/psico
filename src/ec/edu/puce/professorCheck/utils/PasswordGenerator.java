/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.professorCheck.utils;

import java.util.UUID;

/**
 *
 * @author juan
 */
public class PasswordGenerator {

    public static String generatePassword() {
        String pass = UUID.randomUUID().toString();
        pass = pass.replace("-", "");

        if (pass.length() > 8) {
            pass = pass.substring(0, 8);
        }

        return pass;
    }
}
