package org.elearning.project.controller;

import org.elearning.project.services.CourseService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import testutil.Data;
import org.elearning.project.entities.LessonEntity;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static testutil.TestUtil.LESSON_ENTITIES;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class LessonControllerTest {
  @Autowired WebTestClient client;
  @Autowired
  CourseService courseService;


  @BeforeEach
  void initTestData() {
    Data.getCourseList().forEach(course -> this.courseService.saveCourse(course));
  }
  @Test
  void getAllLessons_void_properListOfLessons() {

    this.client
        .get()
        .uri("/lessons")
        .exchange()
        .expectBody()
        .jsonPath("$")
        .value(hasSize(LESSON_ENTITIES.size()));
  }

  @Test
  void deleteLessonById_validId_propelryDeletedLesson() {
    this.client.delete().uri("lessons/2").exchange().expectStatus().isOk();

    this.client
        .get()
        .uri("/lessons/2")
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$.shortDesc")
        .value(equalTo("LESSON_NOT_FOUND"));
  }

  @Test
  void saveLesson_lessonEntity_properlySavedEntity() {
    var lesson = Data.getLesson();

    this.client
        .post()
        .uri("/lessons")
        .bodyValue(lesson)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.title")
        .value(equalTo(lesson.getTitle()))
        .jsonPath("$.articles[0].content")
        .value(equalTo(lesson.getArticles().get(0).getContent()));
  }

  @Test
  void updateLesson_lessonObject_properlyUpdatedLesson() {
    // Given
    var lesson =
        this.client
            .get()
            .uri("/lessons/2")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(LessonEntity.class)
            .returnResult()
            .getResponseBody();

    lesson.setTitle("Updated title!");
    // When
    this.client.put().uri("/lessons").bodyValue(lesson).exchange().expectStatus().isOk();

    // Then
    this.client
        .get()
        .uri("/lessons/2")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.title")
        .value(equalTo("Updated title!"));
  }
}
