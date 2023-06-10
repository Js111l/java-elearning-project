package org.elearning.project.services;

import jakarta.transaction.Transactional;
import org.elearning.project.entities.LessonEntity;
import org.elearning.project.exceptions.CourseNotFoundException;
import org.elearning.project.exceptions.LessonNotFoundException;
import org.elearning.project.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {
  private final CourseRepository courseRepository;
  private final LessonRepository lessonRepository;

  public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository) {
    this.courseRepository = courseRepository;
    this.lessonRepository = lessonRepository;
  }

  public List<CourseEntity> getCourses() {
    return this.courseRepository.findAll();
  }

  public CourseEntity getCourse(int id) {
    return this.courseRepository
        .findById(id)
        .orElseThrow(() -> new CourseNotFoundException("No course with given id found!"));
  }

  public List<CourseEntity> getCourses(String name) {
    var list = this.courseRepository.findByName(name);
    // todo  allow empty?
    if (!list.isEmpty()) {
      return this.courseRepository.findByName(name);
    } else {
      throw new CourseNotFoundException("No course with given id found!");
    }
  }

  public void saveCourse(CourseEntity entity) {
    this.courseRepository.save(entity);
  }

  public void deleteCourse(CourseEntity course) {
    this.courseRepository.delete(course);
  }

  public void deleteCourseById(int courseId) {
    this.courseRepository.deleteById(courseId);
  }

  @Transactional
  public void deleteLessonInCourse(int courseId, int lessonId) {
    this.courseRepository
        .findById(courseId)
        .orElseThrow(() -> new CourseNotFoundException("Course with given id has not been found!"));
    this.lessonRepository
        .findById(lessonId)
        .orElseThrow(() -> new LessonNotFoundException("Lesson with given id has not been found!"));

    this.courseRepository.deleteTextArticleByLessonId(lessonId);
    this.courseRepository.deleteLessonInCourse(courseId, lessonId);
  }

  public List<LessonEntity> getLessonsByCourseId(int id) {
    this.courseRepository
        .findById(id)
        .orElseThrow(() -> new CourseNotFoundException("Course with given id has not been found!"));
    return this.courseRepository.getLessonsByCourseId(id).stream()
        .map(
            x ->
                this.lessonRepository
                    .findById(x)
                    .orElseThrow(
                        () ->
                            new LessonNotFoundException(
                                "Lesson with given id has not been found!")))
        .toList();
  }
}
