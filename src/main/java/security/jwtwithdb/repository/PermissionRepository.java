package security.jwtwithdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.jwtwithdb.entity.UserPermission;

public interface PermissionRepository extends JpaRepository<UserPermission, Long> {
}
