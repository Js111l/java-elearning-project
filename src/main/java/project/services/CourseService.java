package project.services;

import org.springframework.stereotype.Service;
import project.entities.CourseEntity;
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

    public void saveCourse(CourseEntity entity) {
        this.repository.save(entity);
    }
}
