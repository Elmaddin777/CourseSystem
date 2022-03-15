package security.jwtwithdb.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitUsersAndAuthorities implements InitializingBean {
    @Autowired
    private final DbSeeder dbSeeder;

    public InitUsersAndAuthorities(DbSeeder dbSeeder) {
        this.dbSeeder = dbSeeder;
        dbSeeder.createRolesAndPermissions();
        dbSeeder.createUsersAndCourses();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //
    }
}
