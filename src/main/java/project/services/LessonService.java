package project.services;

import org.springframework.stereotype.Service;
import project.entities.LessonEntity;
import project.repository.LessonRepository;

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
