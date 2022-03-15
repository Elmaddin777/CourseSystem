package security.jwtwithdb.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import security.jwtwithdb.configuration.ApplicationUserRole;
import security.jwtwithdb.entity.Course;
import security.jwtwithdb.entity.User;
import security.jwtwithdb.entity.UserPermission;
import security.jwtwithdb.entity.UserRole;
import security.jwtwithdb.repository.CourseRepository;
import security.jwtwithdb.repository.PermissionRepository;
import security.jwtwithdb.repository.RoleRepository;
import security.jwtwithdb.repository.UserRepository;

import static security.jwtwithdb.configuration.ApplicationUserPermission.*;
import static security.jwtwithdb.configuration.ApplicationUserRole.*;

@Component
@RequiredArgsConstructor
public class DbSeeder {
    private final CourseRepository courseRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createRolesAndPermissions(){
        UserPermission studentRead = new UserPermission();
        studentRead.setPermissionName(STUDENT_READ.getPermission());
        studentRead.setPermissionDescription("Read permission for student data");

        UserPermission studentWrite = new UserPermission();
        studentWrite.setPermissionName(STUDENT_WRITE.getPermission());
        studentWrite.setPermissionDescription("Write permission for student data");

        UserPermission courseWrite = new UserPermission();
        courseWrite.setPermissionName(COURSE_WRITE.getPermission());
        courseWrite.setPermissionDescription("Write permission for course data");

        UserPermission courseRead = new UserPermission();
        courseRead.setPermissionName(COURSE_READ.getPermission());
        courseRead.setPermissionDescription("Read permission for course data");

        UserRole adminRole = new UserRole();
        adminRole.setRoleName(ADMIN.name());
        adminRole.setRoleDescription("Admin role");
        adminRole.setPermission(Sets.newHashSet(studentRead, studentWrite, courseWrite, courseRead));

        UserRole teacherRole = new UserRole();
        teacherRole.setRoleName(TEACHER.name());
        teacherRole.setRoleDescription("Teacher role");
        teacherRole.setPermission(Sets.newHashSet(studentRead, courseRead));

        UserRole userRole = new UserRole();
        userRole.setRoleName(STUDENT.name());
        userRole.setRoleDescription("Deafult role for new created students");
        userRole.setPermission(Sets.newHashSet(courseRead));

        permissionRepository.saveAll(Lists.newArrayList(studentRead, studentWrite, courseRead, courseWrite));
        roleRepository.saveAll(Lists.newArrayList(adminRole, teacherRole, userRole));
    }

    public void createUsersAndCourses(){
        Course courseJava = new Course();
        courseJava.setCourseName("Advanced Java Programming");

        Course courseCsharp = new Course();
        courseCsharp.setCourseName("Advanced C# Programming");

        Course coursePhp = new Course();
        coursePhp.setCourseName("Advanced PHP Programming");

        User userAdmin = new User();
        userAdmin.setUserFirstName("Clark");
        userAdmin.setUserLastName("Kent");
        userAdmin.setUserPassword(getEncodedPassword("admin"));
        userAdmin.setUserAuthorities(getUserAuthorities(ADMIN));
        userAdmin.setEnrolledCourses(null);

        User userTeacher = new User();
        userTeacher.setUserFirstName("Bruce");
        userTeacher.setUserLastName("Wayne");
        userTeacher.setUserPassword(getEncodedPassword("teacher"));
        userTeacher.setUserAuthorities(getUserAuthorities(TEACHER));
        userTeacher.setEnrolledCourses(null);

        User userStudent= new User();
        userStudent.setUserFirstName("Barry");
        userStudent.setUserLastName("Allen");
        userStudent.setUserPassword(getEncodedPassword("student"));
        userStudent.setUserAuthorities(getUserAuthorities(STUDENT));
        userStudent.setEnrolledCourses(Sets.newHashSet(courseCsharp, coursePhp));

        courseRepository.saveAll(Lists.newArrayList(courseJava, courseCsharp, coursePhp));
        userRepository.saveAll(Lists.newArrayList(userAdmin, userTeacher, userStudent));
    }

    private String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

    private String getUserAuthorities(ApplicationUserRole role){
       return role.getGrantedAuthorities().toString()
               .replace("[","")
               .replace("]", "");
    }

}
