package org.elearning.project.config;

import org.elearning.project.services.CourseService;
import org.elearning.project.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

    private final CourseService service;

    private final UserService userService;

    public DbInit(CourseService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        Data.getCourseList()
                .forEach(x ->
                        this.service.saveCourse(x)
                );

        Data.getUserList()
                .forEach(
                        x -> this.userService.saveUser(x)
                );
    }
}
