package net.stevencai.security;

import org.mindrot.jbcrypt.BCrypt;

public class LoginVerify {
    private static int wordload = 10;

    /**
     * hash a password
     * @param passwordText plain password text
     * @return hashed password.
     */
    public static String hashPassword(String passwordText){
        String salt = BCrypt.gensalt(wordload);
        return  BCrypt.hashpw(passwordText,salt);
    }

    /**
     * check whether a password is valid.
     * @param password plain password text
     * @param hashedPassword hashed password.
     * @return true if they are equal, false otherwise.
     */
    public static boolean checkPassword(String password, String hashedPassword){
        if(hashedPassword == null || !hashedPassword.startsWith("$2a$")){
            throw new IllegalArgumentException("Invalid hashed password for comparison");
        }
        return BCrypt.checkpw(password,hashedPassword);
    }
}
