package org.elearning.project.controller;

import java.util.stream.Collectors;
import org.elearning.project.entities.LessonEntity;
import org.elearning.project.repository.CourseRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;
import testutil.Data;
import org.elearning.project.services.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.reactive.server.WebTestClient;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class CoursesControllerTest {

  @Autowired WebTestClient webTestClient;

  @Autowired CourseService courseService;

  @BeforeEach
  void initTestData() {
    Data.getCourseList().forEach(course -> this.courseService.saveCourse(course));
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
  }

  @Test
  public void getLessonsByCourseId_properId_properListOfLessons() {
    this.webTestClient
        .get()
        .uri("/courses/1/lessons")
        .exchange()
        .expectBody()
        .jsonPath("$[0].title")
        .isEqualTo("1. Introduction")
        .jsonPath("$[1].title")
        .isEqualTo("2. More details")
        .jsonPath("$", hasSize(2));
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
