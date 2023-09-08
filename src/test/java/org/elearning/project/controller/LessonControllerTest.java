package org.elearning.project.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(Lifecycle.PER_CLASS)
class LessonControllerTest {
  @Autowired WebTestClient client;
  static List<LessonEntity> LESSON_ENTITIES =
      Data.getCourseList().stream().flatMap(x -> x.getLessons().stream()).toList();

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
