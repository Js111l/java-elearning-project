package org.elearning.project.controller;

import com.google.gson.Gson;
import org.elearning.project.config.Data;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.entities.UserEntity;
import org.elearning.project.model.CourseItem;
import org.elearning.project.services.CourseService;
import org.elearning.project.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    @Autowired
    WebTestClient client;

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

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
                .expectBody()
                .jsonPath("$.userUid").value(equalTo("id13"))
                .jsonPath("$.favCourses").value(hasSize(2))
                .jsonPath("$.enrolledCourses").value(hasSize(1));
    }

    @Test
    void getUserById_validId_properUserEntity() {
        this.client
                .get()
                .uri("/users/id5")
                .exchange()
                .expectBody()
                .jsonPath("$.userUid").value(equalTo("id5"))
                .jsonPath("$.favCourses").value(hasSize(0))
                .jsonPath("$.enrolledCourses").value(hasSize(0));
    }

    @Test
    void getUsersFavCourses_validId_properListOfCourses() {
        this.client
                .get()
                .uri("/users/courses/favorites/id12")
                .exchange()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].title").isEqualTo("Subject for beginners")
                .jsonPath("$[0].lessons[0].title").value(equalTo("1. Introduction"))
                .jsonPath("$[0].lessons[1].title").value(equalTo("2. More details"))
                .jsonPath("$", hasSize(3));
    }

    @Test
    void getUsersEnrolledCourses_validId_properListOfCourses() {
        this.client
                .get()
                .uri("/users/courses/enrolled/?id=id12")
                .exchange()
                .expectBody()
                .jsonPath("$").value(hasSize(1))
                .jsonPath("$[0].id").value(equalTo(1))
                .jsonPath("$[0].title").isEqualTo("Subject for beginners")
                .jsonPath("$[0].lessons[0].title").value(equalTo("1. Introduction"))
                .jsonPath("$[0].lessons[1].title").value(equalTo("2. More details"));
    }

    @Test
    void saveToEnrolled_validCourseItem_properlySavedCourse() {

        var course = new CourseItem();
        course.setCourseId(1);
        course.setUserId("id7");

        this.client
                .post()
                .uri("/users/courses/enrolled")
                .bodyValue(course)
                .exchange()
                .expectBody()
                .jsonPath("$.userId").value(equalTo("id7"))
                .jsonPath("$.courseId").value(equalTo(1));

        this.client
                .get()
                .uri("/users/courses/enrolled/?id=id7")
                .exchange()
                .expectBody()
                .jsonPath("$").value(hasSize(1))
                .jsonPath("$[0].id").value(equalTo(1))
                .jsonPath("$[0].title").isEqualTo("Subject for beginners")
                .jsonPath("$[0].lessons[0].title").value(equalTo("1. Introduction"))
                .jsonPath("$[0].lessons[1].title").value(equalTo("2. More details"));
    }

    @Test
    void saveToEnrolled_validCourseItemUserAlreadyEnrolled_properlyAddedCourse() {

        var course = new CourseItem();
        course.setCourseId(2);
        course.setUserId("id11");

        this.client
                .post()
                .uri("/users/courses/enrolled")
                .bodyValue(course)
                .exchange()
                .expectBody()
                .jsonPath("$.userId").value(equalTo("id11"))
                .jsonPath("$.courseId").value(equalTo(2));

        this.client
                .get()
                .uri("/users/courses/enrolled/?id=id11")
                .exchange()
                .expectBody()
                .jsonPath("$").value(hasSize(2))
                .jsonPath("$[1].id").value(equalTo(2))
                .jsonPath("$[1].title").isEqualTo("Subject for beginners")
                .jsonPath("$[1].lessons[0].title").value(equalTo("1. Introduction"))
                .jsonPath("$[1].lessons[1].title").value(equalTo("2. More details"));
    }

    @Test
    void saveToFavorite_validCourseItem_properlySavedCourse() {
        var course = new CourseItem();
        course.setCourseId(1);
        course.setUserId("id7");

        this.client
                .post()
                .uri("/users/courses/favorites")
                .bodyValue(course)
                .exchange()
                .expectBody()
                .jsonPath("$.userId").value(equalTo("id7"))
                .jsonPath("$.courseId").value(equalTo(1));

        System.out.println(this.client
                .get()
                .uri("/users/courses/favorites/?id=id7")
                .exchange()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody());

        //.jsonPath("$[0].id").value(equalTo(1));
              //  .jsonPath("$[0].title").isEqualTo("Subject for beginners")
                //.jsonPath("$[0].lessons[0].title").value(equalTo("1. Introduction"))
              //  .jsonPath("$[0].lessons[1].title").value(equalTo("2. More details"));
    }



    @Test
    void saveToFavorite_validCourseItemUserAlreadyEnrolled_properlyAddedCourse() {

    }
}