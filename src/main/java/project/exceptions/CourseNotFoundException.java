package project.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException() {

    }
}
