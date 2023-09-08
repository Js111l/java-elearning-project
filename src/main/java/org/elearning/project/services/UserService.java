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
import java.util.Set;

@Service
public record UserService(UserRepository userRepository, CourseRepository courseRepository) {

  public void saveUser(UserEntity user) {
    if (!this.userRepository.existsById(user.getUserUid())) {
      user.getFavCourses()
          .forEach(
              x -> {
                this.userRepository.save(user);
                x.setFavCourseUsers(Set.of(user));
                this.courseRepository.save(x);
              });

      user.getEnrolledCourses()
          .forEach(
              x -> {
                this.userRepository.save(user);
                x.setEnrolledCourseUsers(Set.of(user));
                this.courseRepository.save(x);
              });

      this.userRepository.save(user);
    } else {
      throw new RuntimeException();
      // TODO: 10.06.2023
    }
  }

  public void saveCourseToFavorite(String userId, CourseItem courseItem) {
    UserEntity user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with given id not found!"));

    var list = new ArrayList<>(user.getFavCourses());
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
    UserEntity user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with given id not found!"));

    var list = new ArrayList<>(user.getEnrolledCourses());
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
