package org.elearning.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Course")
@Setter
@Getter
@NoArgsConstructor
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;
    private String level;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    private List<LessonEntity> lessons;

    @ManyToMany(mappedBy = "enrolledCourses")
    @JsonBackReference
    private Set<UserEntity> enrolledCourseUsers;

    @ManyToMany(mappedBy = "favCourses")
    @JsonBackReference
    private Set<UserEntity> favCourseUsers;

}
