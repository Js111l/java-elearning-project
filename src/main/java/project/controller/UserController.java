package project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.entities.CourseEntity;
import project.entities.UserEntity;
import project.model.CourseItem;
import project.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity saveUser(@RequestParam String id) {
        this.service.saveUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public UserEntity getUserById(@PathVariable String id) {
        return new ResponseEntity<>(this.service.getUser(id), HttpStatus.OK).getBody();
    }

    @GetMapping("/courses/favorites/{id}")
    @CrossOrigin
    public List<CourseEntity> getUsersFavCourses(@PathVariable String id) {
        return new ResponseEntity<>(this.service.getUsersFavCourses(id), HttpStatus.OK).getBody();
    }

    @PostMapping("/courses/favorites")
    @CrossOrigin
    public ResponseEntity<HttpStatus> saveToFavorite(@RequestBody CourseItem course) {
        this.service.saveCourseToFavorite(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/courses/enrolled/{id}")
    @CrossOrigin
    public List<CourseEntity> getUsersEnrolledCourses(@PathVariable String id) {
        return new ResponseEntity<>(this.service.getUsersEnrolledCourses(id), HttpStatus.OK).getBody();
    }

    @PostMapping("/courses/enrolled")
    @CrossOrigin
    public ResponseEntity<HttpStatus> saveToEnrolled(@RequestBody CourseItem course) {
        this.service.saveCourseToEnrolled(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
