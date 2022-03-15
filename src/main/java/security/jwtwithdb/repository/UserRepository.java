package security.jwtwithdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.jwtwithdb.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserFirstName(String username);
}
