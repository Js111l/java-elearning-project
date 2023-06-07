package org.elearning.project.services;

import org.elearning.project.exceptions.CourseNotFoundException;
import org.springframework.stereotype.Service;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.repository.CourseRepository;

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
        return this.repository.findById(id)
                .orElseThrow(
                        () -> new CourseNotFoundException("No course with given id found!")
                );
    }

    public List<CourseEntity> getCourses(String name) {
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
