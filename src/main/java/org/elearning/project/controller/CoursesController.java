package org.elearning.project.controller;

import org.elearning.project.entities.LessonEntity;
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

  @GetMapping(params = "name")
  @CrossOrigin
  public List<CourseEntity> getCoursesByName(@RequestParam String name) {
    return this.service.getCourses(name);
  }

  @GetMapping("/{id}/lessons")
  @CrossOrigin
  public List<LessonEntity> getLessonsByCourseId(@PathVariable int id) {
    return this.service.getLessonsByCourseId(id);
  }

  @DeleteMapping(params = "courseId")
  public void deleteCourseById(@RequestParam int courseId) {
    this.service.deleteCourseById(courseId);
  }

  @DeleteMapping("/{courseId}/lessons/{lessonId}")
  @CrossOrigin
  public void deleteLessonInCourse(@PathVariable int courseId, @PathVariable int lessonId) {
    this.service.deleteLessonInCourse(courseId, lessonId);
  }

  @PostMapping
  @CrossOrigin
  public CourseEntity saveCourse(@RequestBody CourseEntity course) {
    this.service.saveCourse(course);
    return course;
  }
}
