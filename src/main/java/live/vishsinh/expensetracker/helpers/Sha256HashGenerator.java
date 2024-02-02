package live.vishsinh.expensetracker.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Component
public class Sha256HashGenerator {

    @Value("${app.salt}")
    private String salt;

    public String generateSha256Hash(String input) {
        try {
//            input = input + salt;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // This should never happen
            // SHA-256 is a standard algorithm
            // If it's not available, something is very wrong
            // Log the error and return null
            e.printStackTrace();
            return null;
        }
    }
}
