package org.elearning.project.controller;

import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import util.Data;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.services.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(Lifecycle.PER_CLASS)
public class CoursesControllerTest {

  @Autowired WebTestClient webTestClient;

  @Autowired CourseService courseService;
  static List<CourseEntity> COURSE_LIST = Data.getCourseList();

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
    COURSE_LIST.forEach(course -> this.courseService.saveCourse(course));
  }

  @AfterAll
  void stopTestContainer() {
    MYSQL_CONTAINER.stop();
  }

  @Test
  public void getAllCourses_void_properList() {

    this.webTestClient
        .get()
        .uri("/courses")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo(1)
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$", hasSize(3));
    //        .jsonPath("$[0].lessonIds")
    //        .isArray()
    //        .jsonPath("$[0].lessonIds[0]")
    //        .isEqualTo("1")
    //        .jsonPath("$[0].lessonIds[1]")
    //        .isEqualTo("2");
  }

  @Test
  public void getLessonsByCourseId_properId_properListOfLessons() {
    this.webTestClient
        .get()
        .uri("/courses/1/lessons")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(2))
        .jsonPath("$[0].title")
        .isEqualTo("1. Introduction")
        .jsonPath("$[1].title")
        .isEqualTo("2. More details");
  }

  @Test
  public void getCourse_validCourseId_properCourseEntity() {

    this.webTestClient
        .get()
        .uri("/courses/1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(1)
        .jsonPath("$.title")
        .isEqualTo("Subject for beginners"); // TODO: 05.09.2023
  }

  @Test
  public void getCourseByName_validCourseNamePrefix_properCourseEntity() {
    this.webTestClient
        .get()
        .uri(x -> x.path("/courses").queryParam("name", "Sub").build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$", hasSize(1));
  }

  @Test
  public void getCourseByName_validCourseNameSuffix_properCourseEntity() {
    this.webTestClient
        .get()
        .uri(x -> x.path("/courses").queryParam("name", "beginners").build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].title")
        .isEqualTo("Subject for beginners")
        .jsonPath("$", hasSize(1));
  }

  @Test
  public void getCourseByName_invalidCourseName_courseNotFoundExceptionThrown() {
    // todo allow empty?
    this.webTestClient
        .get()
        .uri(x -> x.path("/courses").queryParam("name", "non-existent course").build())
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$.statusCode")
        .value(equalTo(404))
        .jsonPath("$.shortDesc")
        .value(equalTo("COURSE_NOT_FOUND"));
  }

  @Test
  public void getCourse_invalidCourseId_courseNotFoundExceptionThrown() {
    this.webTestClient
        .get()
        .uri("/courses/99")
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$.statusCode")
        .value(equalTo(404))
        .jsonPath("$.shortDesc")
        .value(equalTo("COURSE_NOT_FOUND"));
  }

  @Test
  void deleteCourseById_properCourseId_properlyDeletedCourse() {
    this.webTestClient.delete().uri("/courses/1").exchange().expectStatus().isOk();
    this.webTestClient.get().uri("/courses/1").exchange().expectStatus().isNotFound();
  }

  @Test
  void deleteLessonInCourse_properIds_properlyDeletedLessonsInCourse() {
    System.out.println(
        this.webTestClient
            .get()
            .uri("/courses")
            .exchange()
            .expectBodyList(CourseEntity.class)
            .returnResult()
            .getResponseBody()
            .stream()
            .map(x -> x.getId())
            .collect(Collectors.toList()));

    this.webTestClient.delete().uri("/courses/1/lessons/1").exchange().expectStatus().isOk();
    this.webTestClient.delete().uri("/courses/1/lessons/2").exchange().expectStatus().isOk();

    this.webTestClient
        .get()
        .uri("/courses/1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.lessons")
        .value(hasSize(0));
  }
}
