package org.elearning.project.services;

import org.elearning.project.entities.LessonEntity;
import org.elearning.project.exceptions.CourseNotFoundException;
import org.elearning.project.model.Course;
import org.elearning.project.model.CourseItem;
import org.springframework.stereotype.Service;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.entities.UserEntity;
import org.elearning.project.exceptions.UserNotFoundException;
import org.elearning.project.repository.CourseRepository;
import org.elearning.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public record UserService(UserRepository userRepository, CourseRepository courseRepository) {

  public void saveUser(UserEntity user) {
    user.getEnrolledCourses()
        .forEach(
            course -> {
              final var optionalCourseEntity = this.courseRepository.findById(course.getId());
              if (optionalCourseEntity.isPresent()) {
                final var fetchedCourse = optionalCourseEntity.get();
                fetchedCourse.addEnrolledUser(user);
                this.courseRepository.save(fetchedCourse);
              } else {
                course.addEnrolledUser(user);
                this.courseRepository.save(course);
              }
            });

    user.getFavCourses()
        .forEach(
            course -> {
              final var optionalCourseEntity = this.courseRepository.findById(course.getId());
              if (optionalCourseEntity.isPresent()) {
                final var fetchedCourse = optionalCourseEntity.get();
                fetchedCourse.addFavUser(user);
                this.courseRepository.save(fetchedCourse);
              } else {
                course.addFavUser(user);
                this.courseRepository.save(course);
              }
            });

    this.userRepository.save(user);
  }

  public void saveCourseToFavorite(String userId, CourseItem courseItem) {
    final var user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with given id not found!"));

    final var list = new ArrayList<>(user.getFavCourses());
    courseItem
        .courseIds()
        .forEach(
            id ->
                list.add(
                    this.courseRepository
                        .findById(id)
                        .orElseThrow(
                            () ->
                                new CourseNotFoundException(
                                    "Course with " + id + "id has not been found!"))));

    user.setFavCourses(list);
    this.userRepository.save(user);
  }

  public void saveCourseToEnrolled(String userId, CourseItem courseItem) {
    final var user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with given id not found!"));

    final var list = new ArrayList<>(user.getEnrolledCourses());
    courseItem
        .courseIds()
        .forEach(
            id ->
                list.add(
                    this.courseRepository
                        .findById(id)
                        .orElseThrow(
                            () ->
                                new CourseNotFoundException(
                                    "Course with " + id + "id has not been found!"))));

    user.setEnrolledCourses(list);
    this.userRepository.save(user);
  }

  public UserEntity getUser(String id) {
    return this.userRepository
        .findById(id)
        .orElseThrow(() -> new UserNotFoundException("User with given id not found!"));
  }

  public List<Course> getUsersEnrolledCourses(String id) {
    return this.userRepository.getEnrolledCourses(id).stream()
        .map(
            x ->
                getCourseModel(
                    this.courseRepository
                        .findById(x)
                        .orElseThrow(
                            () ->
                                new CourseNotFoundException(
                                    "Course with given id has not been found!"))))
        .toList();
  }

  private Course getCourseModel(CourseEntity courseEntity) {
    return new Course(
        courseEntity.getId(),
        courseEntity.getTitle(),
        courseEntity.getShortDescription(),
        courseEntity.getLevel(),
        courseEntity.getLessons().stream().map(LessonEntity::getId).toList());
  }

  public List<Course> getUsersFavCourses(String id) {
    return this.userRepository.getFavCourses(id).stream()
        .map(
            x ->
                getCourseModel(
                    this.courseRepository
                        .findById(x)
                        .orElseThrow(
                            () ->
                                new CourseNotFoundException(
                                    "Course with given id has not been found!"))))
        .toList();
  }

  public List<UserEntity> getUsers() {
    return this.userRepository.findAll();
  }

  public void deleteUser(UserEntity user) {
    this.userRepository.delete(user);
  }

  public void deleteUserById(String userId) {
    this.userRepository
        .findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User with given id has not been found!"));

    this.userRepository.deleteById(userId);
  }
}
