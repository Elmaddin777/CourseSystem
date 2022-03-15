package security.jwtwithdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.jwtwithdb.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
