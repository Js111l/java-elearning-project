package project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "Lesson")
@Setter
@Getter
@NoArgsConstructor
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    @OneToMany(mappedBy = "lesson", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    private List<TextArticleEntity> articles;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private CourseEntity course;
}
