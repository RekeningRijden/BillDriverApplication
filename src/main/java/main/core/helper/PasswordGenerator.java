package main.core.helper;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.domain.User;

/**
 *
 * @author Sam
 */
public class PasswordGenerator {
    
    private PasswordGenerator() {
        // do nothing
    }

    /**
     * Generates an new random password.
     * @param length of the password
     * @return
     */
    public static String generateRandomPassword(int length) {
        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        char[] pass = new char[length];
        for (int i = 0; i < length; i++) {
            pass[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(pass);
    }

    /**
     * Creates a encrypted password.
     * @param username of the user
     * @param password of the user
     * @return the encrypted password.
     */
    public static String encryptPassword(String username, String password) {
        String saltedPassword = saltPassword(username, password);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] data = md.digest(saltedPassword.getBytes());

            return Base64.encodeBase64String(data);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return password;
    }

    /**
     * Creates a salted password.
     * @param username of the user
     * @param password of the user
     * @return the salted password.
     */
    private static String saltPassword(String username, String password) {
        return "nB*R#*&^TG&B^*FGVwskjdfUV@"
                + username
                + password;
    }

}
