package security.jwtwithdb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import security.jwtwithdb.entity.Course;
import security.jwtwithdb.service.CourseService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('course:write')")
    public void createCourse(@RequestBody Course course){
        courseService.createCourse(course);
    }
}
