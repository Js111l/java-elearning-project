package org.elearning.project.controller;

import org.elearning.project.config.Data;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.services.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoursesControllerTest {

  @Autowired WebTestClient webTestClient;

  @Autowired CourseService courseService;
  static List<CourseEntity> COURSE_LIST = Data.getCourseList();

  //    @BeforeAll
  //    void initTestDb() {
  //        COURSE_LIST.forEach(course ->
  //                this.courseService.saveCourse(course)
  //        );
  //    }

  @Test
  public void getAllCourses_void_properList() {

    var responseBody =
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
            .jsonPath("$[0].lessons[0].title")
            .value(equalTo("1. Introduction"))
            .jsonPath("$[0].lessons[1].title")
            .value(equalTo("2. More details"))
            .jsonPath("$", hasSize(3));
  }

  @Test
  public void getLessonsByCourseId_properId_properListOfLessons() {
    this.webTestClient
        .get()
        .uri("/courses/1/lessons")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(2));
  }

  @Test
  public void getCourse_validCourseId_properCourseEntity() {

    var course = Data.getCourseList().stream().findFirst().get();

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
        .isEqualTo("Subject for beginners")
        .jsonPath("$.lessons[0].title")
        .value(equalTo("1. Introduction"))
        .jsonPath("$.lessons[1].title")
        .value(equalTo("2. More details"));
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
