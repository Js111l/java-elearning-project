package project.services;

import org.springframework.stereotype.Service;
import project.entities.CourseEntity;
import project.entities.UserEntity;
import project.exceptions.UserNotFoundException;
import project.model.CourseItem;
import project.repository.CourseRepository;
import project.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public void saveUser(String id) {
        if (!this.userRepository.existsById(id)) {
            this.userRepository.save(new UserEntity(id));
        } else {
            throw new RuntimeException();
        }
    }

    public void saveCourseToFavorite(CourseItem course) {
        UserEntity user = new UserEntity(course.getUserId());
        if (userRepository.existsById(course.getUserId())) {
            user = userRepository.findById(course.getUserId()).get();
        }
        var list = user.getFavCourses();
        if (courseRepository.existsById(course.getCourseId())) {
            list.add(courseRepository.findById(course.getCourseId()).get());
        }
        this.userRepository.save(user);
    }

    public void saveCourseToEnrolled(CourseItem course) {
        UserEntity user = new UserEntity(course.getUserId());
        if (userRepository.existsById(course.getUserId())) {
            user = userRepository.findById(course.getUserId()).get();
        }
        var list = user.getEnrolledCourses();
        if (courseRepository.existsById(course.getCourseId())) {
            list.add(courseRepository.findById(course.getCourseId()).get());
        }
        this.userRepository.save(user);
    }

    public UserEntity getUser(String id) {
        if (this.userRepository.existsById(id)) {
            return this.userRepository.findById(id).get();
        } else {
            throw new UserNotFoundException("User with given id not found!");
        }
    }

    public List<CourseEntity> getUsersEnrolledCourses(String id) {
        return this.userRepository.getEnrolledCourses(id);
    }

    public List<CourseEntity> getUsersFavCourses(String id) {
        return this.userRepository.getFavCourses(id);
    }

}
