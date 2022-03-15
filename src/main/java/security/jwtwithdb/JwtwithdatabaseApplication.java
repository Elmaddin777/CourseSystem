package security.jwtwithdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import security.jwtwithdb.configuration.ApplicationJwtConfig;

@SpringBootApplication
//exclude = {DataSourceAutoConfiguration.class,
//SecurityAutoConfiguration.class}
@EnableConfigurationProperties(ApplicationJwtConfig.class)
public class JwtwithdatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtwithdatabaseApplication.class, args);
    }

}
