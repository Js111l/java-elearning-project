package org.elearning.project.controller;

import org.elearning.project.model.CourseItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static testutil.TestUtil.VALID_ID_1;
import static testutil.TestUtil.VALID_ID_2;
import static testutil.TestUtil.INVALID_ID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTest {
  @Autowired WebTestClient client;
  static CourseItem COURSE_ITEM_WITH_COURSE_ID_2 = new CourseItem(List.of(VALID_ID_2));
  static CourseItem COURSE_ITEM_WITH_COURSE_ID_1 = new CourseItem(List.of(VALID_ID_1));
  static CourseItem COURSE_ITEM_WITH_INVALID_COURSE_ID = new CourseItem(List.of(INVALID_ID));

  @Container
  public static MySQLContainer MYSQL_CONTAINER =
      new MySQLContainer<>("mysql:latest")
          .withDatabaseName("test")
          .withUsername("root")
          .withPassword("qwerty");

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
  }

  @BeforeAll
  void initTestData() {
    MYSQL_CONTAINER.start();
  }

  @AfterAll
  void stopTestContainer() {
    MYSQL_CONTAINER.stop();
  }

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
        .uri("/users/id12/courses/favorites")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo(1)
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessonIds")
        .isArray()
        .jsonPath("$[0].lessonIds[0]")
        .isEqualTo("1")
        .jsonPath("$[0].lessonIds[1]")
        .isEqualTo("2");
  }

  @Test
  void getUsersEnrolledCourses_validId_properListOfCourses() {
    this.client
        .get()
        .uri("/users/id12/courses/enrolled")
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
        .jsonPath("$[0].lessonIds")
        .isArray()
        .jsonPath("$[0].lessonIds[0]")
        .isEqualTo("1")
        .jsonPath("$[0].lessonIds[1]")
        .isEqualTo("2");
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

    this.client
        .post()
        .uri("/users/id7/courses/enrolled")
        .bodyValue(COURSE_ITEM_WITH_COURSE_ID_1)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.courseIds")
        .value(equalTo(List.of(1)));

    this.client
        .get()
        .uri("/users/id7/courses/enrolled")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(1))
        .jsonPath("$[0].id")
        .value(equalTo(1))
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessonIds")
        .isArray()
        .jsonPath("$[0].lessonIds[0]")
        .isEqualTo("1")
        .jsonPath("$[0].lessonIds[1]")
        .isEqualTo("2");
  }

  @Test
  void saveToEnrolled_validCourseItemUserAlreadyEnrolled_properlyAddedCourse() {

    this.client
        .post()
        .uri("/users/id5/courses/enrolled")
        .bodyValue(COURSE_ITEM_WITH_COURSE_ID_2)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.courseIds")
        .value(equalTo(List.of(2)));

    this.client
        .get()
        .uri("/users/id5/courses/enrolled")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(1))
        .jsonPath("$[0].id")
        .value(equalTo(2))
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].level")
        .isEqualTo("beginner")
        .jsonPath("$[0].lessonIds")
        .isArray()
        .jsonPath("$[0].lessonIds[0]")
        .isEqualTo("3")
        .jsonPath("$[0].lessonIds[1]")
        .isEqualTo("4");
  }

  @Test
  void saveToFavorite_validCourseItem_properlySavedCourse() {

    this.client
        .post()
        .uri("/users/id7/courses/favorites")
        .bodyValue(COURSE_ITEM_WITH_COURSE_ID_2)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.courseIds")
        .value(equalTo(List.of(2)));

    this.client
        .get()
        .uri("/users/id7/courses/favorites")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(1))
        .jsonPath("$[0].id")
        .value(equalTo(2))
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessonIds")
        .isArray()
        .jsonPath("$[0].lessonIds[0]")
        .isEqualTo("3")
        .jsonPath("$[0].lessonIds[1]")
        .isEqualTo("4");
  }

  @Test
  void saveToFavorite_validCourseItemUserAlreadyEnrolled_properlyAddedCourse() {

    this.client
        .post()
        .uri("/users/id11/courses/favorites")
        .bodyValue(COURSE_ITEM_WITH_COURSE_ID_2)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.courseIds")
        .value(equalTo(List.of(2)));

    this.client
        .get()
        .uri("/users/id11/courses/favorites")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(2))
        .jsonPath("$[0].id")
        .value(equalTo(1))
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$[0].lessonIds")
        .isArray()
        .jsonPath("$[0].lessonIds[0]")
        .isEqualTo("1")
        .jsonPath("$[0].lessonIds[1]")
        .isEqualTo("2");
  }

  @Test
  void saveToFavorite_nonExistentUser_userNotFoundExceptionThrown() {

    this.client
        .post()
        .uri("/users/INVALID_ID/courses/favorites")
        .bodyValue(COURSE_ITEM_WITH_COURSE_ID_2)
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
    this.client
        .post()
        .uri("/users/id5/courses/favorites")
        .bodyValue(COURSE_ITEM_WITH_INVALID_COURSE_ID)
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
