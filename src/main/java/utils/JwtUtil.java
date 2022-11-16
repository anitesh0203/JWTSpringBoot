package utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

    private static Map<String, Object> claimSet;
    private static final String SECRET_KEY = "darkHorse";

    public String generateToken(UserDetails userDetails) {
        // what is claims???
        Map<String, Object> claims =  new HashMap<>();
        claims.put("iss", "MASTERCARD INC");
        return createToken(claims, userDetails.getUsername());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        claimSet = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody();
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    // helpers methods below this point


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }



    private boolean isTokenExpired(String token) {
        Date expDate = (Date) claimSet.get("exp");
        return expDate.before(new Date());
    }

    public String extractUserName(String token) {
        if(claimSet == null) {
            claimSet = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        }
       return  (String) getClaimSet().get("sub");
    }

    public static Map<String, Object> getClaimSet() {
        return claimSet;
    }

}
