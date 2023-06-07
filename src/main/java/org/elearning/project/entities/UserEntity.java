package org.elearning.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserEntity {
    @Id
    private String userUid;

    @ManyToMany
    @JoinTable(name = "enrolled_courses")
    @JsonManagedReference
    private List<CourseEntity> enrolledCourses = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "fav_courses")
    @JsonManagedReference
    private List<CourseEntity> favCourses = new ArrayList<>();

    public UserEntity(String userUid) {
        this.userUid = userUid;
    }

    public List<CourseEntity> getFavCourses() {
        return new ArrayList<>(favCourses);
    }

    public List<CourseEntity> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }

    public void addEnrolledCourse(CourseEntity courseId) {
        if (this.enrolledCourses == null) {
            this.enrolledCourses = new ArrayList<>();
        }
        this.enrolledCourses.add(courseId);
    }

    public void addFavCourse(CourseEntity courseId) {
        if (this.favCourses == null) {
            this.favCourses = new ArrayList<>();
        }
        this.favCourses.add(courseId);
    }
}


