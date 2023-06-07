package org.elearning.project.exceptions.handler;

import org.elearning.project.exceptions.CourseNotFoundException;
import org.elearning.project.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.elearning.project.exceptions.UserNotFoundException;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorModel> handleCourseNOtFound(CourseNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorModel(
                        HttpStatus.NOT_FOUND.value(),
                        "COURSE_NOT_FOUND",
                        exception.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorModel> handleUserNotFound(UserNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorModel(
                        HttpStatus.NOT_FOUND.value(),
                        "USER_NOT_FOUND",
                        exception.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

}
