package security.jwtwithdb.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class JwtRequest {
    String userName;

    String userPassword;
}
