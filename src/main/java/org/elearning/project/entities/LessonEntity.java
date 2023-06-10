package org.elearning.project.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Lesson")
@Setter
@Getter
@NoArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = LessonEntity.class)
public class LessonEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;

  @OneToMany(mappedBy = "lesson", cascade = CascadeType.PERSIST)
  private List<TextArticleEntity> articles;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private CourseEntity course;
}
