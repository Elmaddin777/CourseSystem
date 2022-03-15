package security.jwtwithdb.configuration;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static security.jwtwithdb.configuration.ApplicationUserPermission.*;

@RequiredArgsConstructor
@Getter
public enum ApplicationUserRole {
    ADMIN (Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    TEACHER (Sets.newHashSet(COURSE_READ, STUDENT_READ)),
    STUDENT (Sets.newHashSet(COURSE_READ));

    private final Set<ApplicationUserPermission> permissions;

    public Set<GrantedAuthority> getGrantedAuthorities(){
        Set<GrantedAuthority> authorities = getPermissions().stream()
                .map(applicationUserPermission -> new SimpleGrantedAuthority(applicationUserPermission.getPermission()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
