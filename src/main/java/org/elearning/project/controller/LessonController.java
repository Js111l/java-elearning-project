package org.elearning.project.controller;

import org.elearning.project.entities.LessonEntity;
import org.elearning.project.services.LessonService;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessons")
public class LessonController {
  private final LessonService service;

  public LessonController(LessonService service) {
    this.service = service;
  }

  @GetMapping
  @CrossOrigin
  public List<LessonEntity> getAllLessons() {
    return this.service.getAll();
  }

  @GetMapping("/{id}")
  @CrossOrigin
  public LessonEntity getLessonById(@PathVariable(name = "id") int id) {
    return this.service.getLessonById(id);
  }

  @DeleteMapping("/{lessonId}")
  @CrossOrigin
  public void deleteLessonById(@PathVariable int lessonId) {
    this.service.deleteLessonById(lessonId);
  }

  @PostMapping
  @CrossOrigin
  public LessonEntity saveLesson(@RequestBody LessonEntity lessonEntity) {
    return this.service.saveLesson(lessonEntity);
  }

  @PutMapping
  @CrossOrigin
  public LessonEntity updateLesson(@RequestBody LessonEntity lessonEntity) {
    return this.service.updateLesson(lessonEntity);
  }
}