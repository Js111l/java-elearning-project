package org.elearning.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.elearning.project.entities.LessonEntity;
import org.elearning.project.services.LessonService;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @PostMapping
    @CrossOrigin
    public LessonEntity createLesson(@RequestBody LessonEntity lessonEntity) {
        service.saveLesson(lessonEntity);
        return lessonEntity;
    }
}
