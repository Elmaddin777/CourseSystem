package security.jwtwithdb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import security.jwtwithdb.entity.Course;
import security.jwtwithdb.repository.CourseRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        return  courseRepository.findAll();
    }

    public void createCourse(Course course){
        courseRepository.save(course);
    }
}
