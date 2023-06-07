package org.elearning.project.controller;

import org.elearning.project.model.CourseItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.entities.UserEntity;
import org.elearning.project.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @CrossOrigin
    public UserEntity saveUser(@RequestParam UserEntity user) {
        this.service.saveUser(user);
        return user;
    }

    @GetMapping("")
    @CrossOrigin
    public List<UserEntity> getAllUser() {
        return this.service.getUsers();
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public UserEntity getUserById(@PathVariable String id) {
        return this.service.getUser(id);
    }

    @GetMapping("/courses/favorites/")
    @CrossOrigin
    public List<CourseEntity> getUsersFavCourses(@RequestParam String id) {
        return this.service.getUsersFavCourses(id);
    }

    @GetMapping("/courses/enrolled/")
    @CrossOrigin
    public List<CourseEntity> getUsersEnrolledCourses(@RequestParam String id) {
        return this.service.getUsersEnrolledCourses(id);
    }


    @PostMapping("/courses/enrolled")
    @CrossOrigin
    public CourseItem saveToEnrolled(@RequestBody CourseItem course) {
        this.service.saveCourseToEnrolled(course);
        return course;
    }

    @PostMapping("/courses/favorites")
    @CrossOrigin
    public CourseItem saveToFavorite(@RequestBody CourseItem course) {
        this.service.saveCourseToFavorite(course);
        return course;
    }
}
