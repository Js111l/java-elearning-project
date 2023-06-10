package org.elearning.project.controller;

import org.elearning.project.config.Data;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.model.CourseItem;
import org.elearning.project.services.CourseService;
import org.elearning.project.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
  @Autowired WebTestClient client;

  @Autowired UserService userService;

  @Autowired CourseService courseService;

  static List<CourseEntity> COURSE_LIST = Data.getCourseList();

  //    @BeforeAll
  //    void initTestDb() {
  //        COURSE_LIST.forEach(course ->
  //                this.courseService.saveCourse(course)
  //        );
  //
  //    }

  @Test
  void getUserById_idOfUserWithFavCourses_properUserEntity() {
    this.client
        .get()
        .uri("/users/id13")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.userUid")
        .value(equalTo("id13"))
        .jsonPath("$.favCourses")
        .value(hasSize(2))
        .jsonPath("$.enrolledCourses")
        .value(hasSize(1));
  }

  @Test
  void getUserById_validId_properUserEntity() {
    this.client
        .get()
        .uri("/users/id5")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.userUid")
        .value(equalTo("id5"))
        .jsonPath("$.favCourses")
        .value(hasSize(0))
        .jsonPath("$.enrolledCourses")
        .value(hasSize(0));
  }

  @Test
  void getUsersFavCourses_validId_properListOfCourses() {
    this.client
        .get()
        .uri("/users/courses/favorites/id12")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo(1)
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessons[0].title")
        .value(equalTo("1. Introduction"))
        .jsonPath("$[0].lessons[1].title")
        .value(equalTo("2. More details"))
        .jsonPath("$", hasSize(3));
  }

  @Test
  void getUsersEnrolledCourses_validId_properListOfCourses() {
    this.client
        .get()
        .uri("/users/courses/enrolled/?id=id12")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(1))
        .jsonPath("$[0].id")
        .value(equalTo(1))
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessons[0].title")
        .value(equalTo("1. Introduction"))
        .jsonPath("$[0].lessons[1].title")
        .value(equalTo("2. More details"));
  }

  @Test
  void deleteUserById_validId_properlyDeletedUser() {
    this.client.delete().uri("/users/id5").exchange().expectStatus().isOk();
    this.client.get().uri("/users/id5").exchange().expectStatus().isNotFound();
  }

  @Test
  void deleteUserById_inValidId_userNotFoundExceptionThrown() {
    this.client
        .delete()
        .uri("/users/id99")
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$.shortDesc")
        .value(equalTo("USER_NOT_FOUND"));
  }

  @Test
  void saveToEnrolled_validCourseItem_properlySavedCourse() {

    var course = new CourseItem(List.of(1));

    this.client
        .post()
        .uri("/users/courses/enrolled")
        .bodyValue(course)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.userId")
        .value(equalTo("id7"))
        .jsonPath("$.courseId")
        .value(equalTo(1));

    this.client
        .get()
        .uri("/users/courses/enrolled/?id=id7")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(1))
        .jsonPath("$[0].id")
        .value(equalTo(1))
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessons[0].title")
        .value(equalTo("1. Introduction"))
        .jsonPath("$[0].lessons[1].title")
        .value(equalTo("2. More details"));
  }

  @Test
  void saveToEnrolled_validCourseItemUserAlreadyEnrolled_properlyAddedCourse() {

    var course = new CourseItem(List.of(2));

    this.client
        .post()
        .uri("/users/courses/enrolled")
        .bodyValue(course)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.userId")
        .value(equalTo("id11"))
        .jsonPath("$.courseId")
        .value(equalTo(2));

    this.client
        .get()
        .uri("/users/courses/enrolled/?id=id11")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(2))
        .jsonPath("$[1].id")
        .value(equalTo(2))
        .jsonPath("$[1].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[1].lessons[0].title")
        .value(equalTo("1. Introduction"))
        .jsonPath("$[1].lessons[1].title")
        .value(equalTo("2. More details"));
  }

  @Test
  void saveToFavorite_validCourseItem_properlySavedCourse() {
    var course = new CourseItem(List.of(2));

    this.client
        .post()
        .uri("/users/courses/favorites")
        .bodyValue(course)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.userId")
        .value(equalTo("id7"))
        .jsonPath("$.courseId")
        .value(equalTo(2));

    this.client
        .get()
        .uri("/users/courses/favorites/?id=id7")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(1))
        .jsonPath("$[0].id")
        .value(equalTo(2))
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessons[0].title")
        .value(equalTo("1. Introduction"))
        .jsonPath("$[0].lessons[1].title")
        .value(equalTo("2. More details"));
  }

  @Test
  void saveToFavorite_validCourseItemUserAlreadyEnrolled_properlyAddedCourse() {

    var course = new CourseItem(List.of(2));

    this.client
        .post()
        .uri("/users/courses/favorites")
        .bodyValue(course)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.userId")
        .value(equalTo("id11"))
        .jsonPath("$.courseId")
        .value(equalTo(2));

    this.client
        .get()
        .uri("/users/courses/favorites/?id=id11")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(2))
        .jsonPath("$[0].id")
        .value(equalTo(1))
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessons[0].title")
        .value(equalTo("1. Introduction"))
        .jsonPath("$[0].lessons[1].title")
        .value(equalTo("2. More details"));
  }

  @Test
  void saveToFavorite_nonExistentUser_userNotFoundExceptionThrown() {

    var course = new CourseItem(List.of(2));

    this.client
        .post()
        .uri("/users/courses/favorites")
        .bodyValue(course)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$.statusCode")
        .value(equalTo(404))
        .jsonPath("$.shortDesc")
        .value(equalTo("USER_NOT_FOUND"));
  }

  @Test
  void saveToFavorite_nonExistentCourse_courseNotFoundExceptionThrown() {

    var course = new CourseItem(List.of(21));

    this.client
        .post()
        .uri("/users/id5/courses/favorites")
        .bodyValue(course)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$.statusCode")
        .value(equalTo(404))
        .jsonPath("$.shortDesc")
        .value(equalTo("COURSE_NOT_FOUND"));
  }
}
