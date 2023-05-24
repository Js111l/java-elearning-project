package project.services;

import org.springframework.stereotype.Service;
import project.entities.CourseEntity;
import project.exceptions.CourseNotFoundException;
import project.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<CourseEntity> getCourses() {
        return this.repository.findAll();
    }

    public CourseEntity getCourse(int id) {
        if (this.repository.findById(id).isPresent()) {
            return this.repository.findById(id).get();
        } else {
            throw new CourseNotFoundException("No course with given id found!");
        }
    }

    public CourseEntity getCourse(String name) {
        if (this.repository.findByName(name) != null) {
            return this.repository.findByName(name);
        } else {
            throw new CourseNotFoundException("No course with given id found!");
        }
    }

    public void saveCourse(CourseEntity entity) {
        this.repository.save(entity);
    }
}
