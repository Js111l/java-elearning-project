package org.elearning.project.controller;

import org.elearning.project.model.CourseItem;
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
  public List<CourseEntity> getUsersFavCourses(@PathVariable String userId) {
    return this.service.getUsersFavCourses(userId);
  }

  @GetMapping("{userId}/courses/enrolled")
  @CrossOrigin
  public List<CourseEntity> getUsersEnrolledCourses(@PathVariable String userId) {
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
    return course;
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
