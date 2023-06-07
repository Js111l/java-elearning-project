package org.elearning.project.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.services.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    private final CourseService service;

    public CoursesController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    @CrossOrigin
    public List<CourseEntity> getAllCourses() {
        return this.service.getCourses();
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public CourseEntity getCourse(@PathVariable int id) {
        return this.service.getCourse(id);
    }

    @GetMapping("/")
    @CrossOrigin
    public List<CourseEntity> getCourseByName(@RequestParam String name) {
        return this.service.getCourses(name);
    }

    @PostMapping(consumes = "application/json")
    @CrossOrigin
    public CourseEntity saveCourse(@RequestBody CourseEntity course) {
        this.service.saveCourse(course);
        return course;
    }

}
