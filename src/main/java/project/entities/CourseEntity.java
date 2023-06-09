package project.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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
    @ManyToMany
    @JoinTable(name = "enrolled_courses")
    private Set<UserEntity> enrolledCourseUsers;

    @ManyToMany
    @JoinTable(name = "fav_courses")
    private Set<UserEntity> favCourseUsers;

}
