package security.jwtwithdb.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    String roleName;

    String roleDescription;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "authorities",
            joinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "PERMISSION_ID")
            }
    )
    Set<UserPermission> permission;

}
