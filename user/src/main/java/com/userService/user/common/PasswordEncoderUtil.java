package com.userService.user.common;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoderUtil {

    // Hash password with salt
    public static String encode(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify password during login
    public static boolean matches(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
