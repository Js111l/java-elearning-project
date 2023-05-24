package project.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
public class UserEntity {
    @Id
    private String userUid;

    @ManyToMany(mappedBy = "enrolledCourseUsers")
    private List<CourseEntity> enrolledCourses=new ArrayList<>();

    @ManyToMany(mappedBy = "favCourseUsers")
    private List<CourseEntity> favCourses=new ArrayList<>();

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


