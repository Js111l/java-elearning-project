package org.elearning.project.services;

import org.elearning.project.model.CourseItem;
import org.springframework.stereotype.Service;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.entities.UserEntity;
import org.elearning.project.exceptions.UserNotFoundException;
import org.elearning.project.repository.CourseRepository;
import org.elearning.project.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public void saveUser(UserEntity user) {
        if (!this.userRepository.existsById(user.getUserUid())) {
            user.getFavCourses().forEach(x -> {
                this.userRepository.save(user);
                x.setEnrolledCourseUsers(Set.of(user));
                this.courseRepository.save(x);
            });
            user.getEnrolledCourses().forEach(x -> {
                this.userRepository.save(user);
                x.setEnrolledCourseUsers(Set.of(user));
                this.courseRepository.save(x);
            });
            this.userRepository.save(user);
        } else {
            throw new RuntimeException();
        }
    }

    public void saveCourseToFavorite(CourseItem course) {
        UserEntity user = userRepository.findById(course.getUserId()).orElseThrow();
        var list = user.getFavCourses();
        list.add(courseRepository.findById(course.getCourseId()).orElseThrow());
        user.setFavCourses(list);
        this.userRepository.save(user);
    }

    public void saveCourseToEnrolled(CourseItem course) {
        UserEntity user = userRepository.findById(course.getUserId()).orElseThrow();
        var list = user.getEnrolledCourses();
        list.add(courseRepository.findById(course.getCourseId()).orElseThrow());
        user.setEnrolledCourses(list);
        this.userRepository.save(user);
    }

    public UserEntity getUser(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User with given id not found!")
                );
    }

    public List<CourseEntity> getUsersEnrolledCourses(String id) {
        return this.userRepository.getEnrolledCourses(id)
                .stream()
                .map(x -> this.courseRepository.findById(x).orElseThrow())
                .toList();
    }

    public List<CourseEntity> getUsersFavCourses(String id) {
        return this.userRepository.getFavCourses(id)
                .stream()
                .map(x -> this.courseRepository.findById(x).orElseThrow())
                .toList();
    }

    public List<UserEntity> getUsers() {
        return this.userRepository.findAll();
    }
}
