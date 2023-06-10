package org.elearning.project.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Course")
@Setter
@Getter
@NoArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = CourseEntity.class)
public class CourseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;

  @Column(columnDefinition = "TEXT")
  private String shortDescription;

  private String level;

  @OneToMany(mappedBy = "course")
  private List<LessonEntity> lessons;

  @ManyToMany(mappedBy = "enrolledCourses")
  private Set<UserEntity> enrolledCourseUsers;

  @ManyToMany(mappedBy = "favCourses")
  private Set<UserEntity> favCourseUsers;
}
