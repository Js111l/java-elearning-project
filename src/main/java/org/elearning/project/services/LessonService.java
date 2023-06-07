package org.elearning.project.services;

import org.springframework.stereotype.Service;
import org.elearning.project.entities.LessonEntity;
import org.elearning.project.repository.LessonRepository;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository repository) {
        this.lessonRepository = repository;
    }

    public void saveLesson(LessonEntity lessonEntity) {
        this.lessonRepository.save(lessonEntity);
    }
}
