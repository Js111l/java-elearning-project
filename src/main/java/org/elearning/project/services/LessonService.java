package org.elearning.project.services;

import org.elearning.project.exceptions.LessonNotFoundException;
import org.springframework.stereotype.Service;
import org.elearning.project.entities.LessonEntity;
import org.elearning.project.repository.LessonRepository;

import java.util.List;

@Service
public class LessonService {
  private final LessonRepository lessonRepository;

  public LessonService(LessonRepository lessonRepository) {
    this.lessonRepository = lessonRepository;
  }

  public LessonEntity saveLesson(LessonEntity lessonEntity) {
    this.lessonRepository.save(lessonEntity);
    return lessonEntity;
  }

  public List<LessonEntity> getAll() {
    return this.lessonRepository.findAll();
  }

  public void deleteLesson(LessonEntity lesson) {
    this.lessonRepository.delete(lesson);
  }

  public void deleteLessonById(int lessonId) {
    this.lessonRepository
        .findById(lessonId)
        .orElseThrow(() -> new LessonNotFoundException("Lesson with given id has not been found!"));
    this.lessonRepository.deleteById(lessonId);
  }

  public LessonEntity updateLesson(LessonEntity entity) {
    this.lessonRepository
        .findById(entity.getId())
        .orElseThrow(() -> new LessonNotFoundException("Lesson with given id has not been found!"));
    this.lessonRepository.save(entity);
    return entity;
  }

  public LessonEntity getLessonById(int id) {
    return this.lessonRepository
        .findById(id)
        .orElseThrow(() -> new LessonNotFoundException("Lesson with given id has not been found!"));
  }
}
