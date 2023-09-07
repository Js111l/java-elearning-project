package testutil;

import org.elearning.project.services.CourseService;
import org.elearning.project.services.LessonService;
import org.elearning.project.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

  private final CourseService service;
  private final UserService userService;
  private final LessonService lessonService;

  public DbInit(CourseService service, UserService userService, LessonService lessonService) {
    this.service = service;
    this.userService = userService;
    this.lessonService = lessonService;
  }

  @Override
  public void run(String... args) {
    Data.getCourseList()
        .forEach(
            courseEntity -> {
              this.service.saveCourse(courseEntity);
              courseEntity
                  .getLessons()
                  .forEach(this.lessonService::saveLesson);
            });

    Data.getUserList().forEach(this.userService::saveUser);
  }
}
