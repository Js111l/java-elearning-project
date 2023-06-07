package org.elearning.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elearning.project.config.Data;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.services.CourseService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoursesControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    CourseService courseService;
    static List<CourseEntity> COURSE_LIST = Data.getCourseList();

//    @BeforeAll
//    void initTestDb() {
//        COURSE_LIST.forEach(course ->
//                this.courseService.saveCourse(course)
//        );
//    }

    @Test
    public void getAllCourses_void_properList() {

        var responseBody = this.webTestClient
                .get()
                .uri("/courses")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].title").isEqualTo("Subject for beginners")
                .jsonPath("$[0].lessons[0].title").value(equalTo("1. Introduction"))
                .jsonPath("$[0].lessons[1].title").value(equalTo("2. More details"))
                .jsonPath("$", hasSize(3));
    }

    @Test
    public void getCourse_5_properCourseEntity() throws JsonProcessingException {

        var course = Data.getCourseList().stream().findFirst().get();

        this.webTestClient
                .get()
                .uri("/courses/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.title").isEqualTo("Subject for beginners")
                .jsonPath("$.lessons[0].title").value(equalTo("1. Introduction"))
                .jsonPath("$.lessons[1].title").value(equalTo("2. More details"));

    }

    @Test
    public void getCourseByName_Sub_properCourseEntity() {
        this.webTestClient
                .get()
                .uri(x -> x.path("/courses/").queryParam("name", "Sub").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("Subject for beginners")
                .jsonPath("$", hasSize(1));
    }

    @Test
    public void getCourseByName_beginners_properCourseEntity() {
        this.webTestClient
                .get()
                .uri(x -> x.path("/courses/").queryParam("name", "beginners").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("Subject for beginners")
                .jsonPath("$", hasSize(1));
    }

}