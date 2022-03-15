package security.jwtwithdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.jwtwithdb.entity.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
}
