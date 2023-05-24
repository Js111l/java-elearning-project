package project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.entities.LessonEntity;
import project.services.LessonService;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<HttpStatus> createLesson(@RequestBody LessonEntity lessonEntity) {
        service.saveLesson(lessonEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
