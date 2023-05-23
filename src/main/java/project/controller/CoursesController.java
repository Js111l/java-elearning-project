package project.controller;

;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.entities.CourseEntity;
import project.services.CourseService;

import java.util.List;
@RestController
@RequestMapping("/courses")
public class CoursesController {
    private CourseService service;

    public CoursesController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    @CrossOrigin
    public List<CourseEntity> getAllCourses() {
        return new ResponseEntity<>(service.getCourses(), HttpStatus.OK).getBody();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveCourse(CourseEntity course) {
        this.service.saveCourse(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
