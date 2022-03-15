package security.jwtwithdb.configuration;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Configuration
public class ApplicationSecretKey {

    private final ApplicationJwtConfig applicationJwtConfig;

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(
                applicationJwtConfig.getSecretKey().getBytes()
        );
    }
}
