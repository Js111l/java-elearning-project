package org.elearning.project.controller;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LessonControllerTest {


}