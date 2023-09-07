package org.elearning.project.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @OneToMany(
      mappedBy = "course",
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
  private List<LessonEntity> lessons;

  @ManyToMany(mappedBy = "enrolledCourses", cascade = CascadeType.REMOVE)
  private Set<UserEntity> enrolledCourseUsers = new HashSet<>();

  @ManyToMany(mappedBy = "favCourses")
  private Set<UserEntity> favCourseUsers = new HashSet<>();

  public void addFavUser(UserEntity user) {
    favCourseUsers.add(user);
  }

  public void addEnrolledUser(UserEntity user) {
    enrolledCourseUsers.add(user);
  }
}
