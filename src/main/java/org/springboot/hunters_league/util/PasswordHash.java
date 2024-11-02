package org.springboot.hunters_league.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {

    // Hash the plain password using BCrypt
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Check if the plain password matches the hashed password
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
