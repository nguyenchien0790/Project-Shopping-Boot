package ra.shopping.security.jwt;

import lombok.extern.slf4j.Slf4j;
import ra.shopping.security.userprinciple.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "nguyenchien0790@gmail.com";
    private int jwtExpiration = 86400000;

    //tao JWT tu thong tin cua user
    public String createToken(Authentication authentication){
        Date now =new Date();
        Date dateEnd = new Date(now.getTime()+jwtExpiration);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        // tao chuoi JWT tu username duy nhat danh cho moi user
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(now)
                .setExpiration(dateEnd)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            log.error("Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
        return false;
    }

    // lay thong tin user tu JWT
    public String getUerNameFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
//        String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return claims.getSubject();
    }
}
