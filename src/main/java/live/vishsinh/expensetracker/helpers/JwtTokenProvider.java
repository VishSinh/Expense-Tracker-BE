package live.vishsinh.expensetracker.helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private int jwtExpiration;

    public String generateToken(String userIdHash) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        System.out.println("expiration: " + jwtExpiration);
        System.out.println("jwtSecret: " + jwtSecret);
        System.out.println("userIdHash: " + userIdHash);

        return Jwts.builder()
                .setSubject(userIdHash)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

}
