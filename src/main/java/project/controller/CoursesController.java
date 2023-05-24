package project.controller;

;


import jakarta.persistence.Column;
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

    @GetMapping("/{id}")
    @CrossOrigin
    public CourseEntity getCourse(@PathVariable int id) {
        return new ResponseEntity<>(service.getCourse(id), HttpStatus.OK).getBody();
    }

    @GetMapping("/{name}")
    @CrossOrigin
    public CourseEntity getCourseByName(@PathVariable String name) {
        return new ResponseEntity<>(service.getCourse(name), HttpStatus.OK).getBody();
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<HttpStatus> saveCourse(@RequestBody CourseEntity course) {
        this.service.saveCourse(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
