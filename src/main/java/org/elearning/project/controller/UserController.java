package org.elearning.project.controller;

import org.elearning.project.model.Course;
import org.elearning.project.model.CourseItem;
import org.elearning.project.entities.UserEntity;
import org.elearning.project.services.UserService;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  @CrossOrigin
  public List<UserEntity> getAllUser() {
    return this.service.getUsers();
  }

  @GetMapping("/{id}")
  @CrossOrigin
  public UserEntity getUserById(@PathVariable String id) {
    return this.service.getUser(id);
  }

  @GetMapping("{userId}/courses/favorites")
  @CrossOrigin
  public List<Course> getUsersFavCourses(@PathVariable String userId) {
    return this.service.getUsersFavCourses(userId);
  }

  @GetMapping("{userId}/courses/enrolled")
  @CrossOrigin
  public List<Course> getUsersEnrolledCourses(@PathVariable String userId) {
    return this.service.getUsersEnrolledCourses(userId);
  }

  @DeleteMapping("/{userId}")
  @CrossOrigin
  public void deleteUserById(@PathVariable String userId) {
    this.service.deleteUserById(userId);
  }

  @PostMapping("{userId}/courses/enrolled")
  @CrossOrigin
  public CourseItem saveToEnrolled(@PathVariable String userId, @RequestBody CourseItem course) {
    this.service.saveCourseToEnrolled(userId, course);
    return course; // TODO: 05.09.2023
  }

  @PostMapping("{userId}/courses/favorites")
  @CrossOrigin
  public CourseItem saveToFavorite(@PathVariable String userId, @RequestBody CourseItem course) {
    this.service.saveCourseToFavorite(userId, course);
    return course;
  }

  @PostMapping
  @CrossOrigin
  public UserEntity saveUser(@RequestParam UserEntity user) {
    this.service.saveUser(user);
    return user;
  }
}
