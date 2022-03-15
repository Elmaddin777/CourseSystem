package security.jwtwithdb.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import security.jwtwithdb.configuration.ApplicationJwtConfig;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final ApplicationJwtConfig applicationJwtConfig;
    private final SecretKey secretKey;

    public String generateToken(Authentication authResult) {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now()
                        .plusDays(applicationJwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        return applicationJwtConfig.getTokenPrefix() + token;
    }

    private Jws<Claims> parseToken(String token){
        try{
            return  Jwts.parserBuilder()
                    .setSigningKey(applicationJwtConfig.getSecretKey().getBytes())
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e){
            throw new IllegalStateException(
                    String.format("Token cannot be trusted, error is %s", e.getMessage()));
        }
    }

    public boolean isTokenExpired(String token){
        Date tokenExpDate = parseToken(token).getBody().getExpiration();
        return tokenExpDate.before(new Date());
    }

    public String getUsernameFromToken(String token){
        return parseToken(token).getBody().getSubject();
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthoritiesFromToken(String token){
        var authorities = (List<Map<String, String>>) parseToken(token).getBody().get("authorities");
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                .collect(Collectors.toSet());
    }
}
