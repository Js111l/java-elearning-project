package org.elearning.project.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "userUid",
    scope = UserEntity.class)
public class UserEntity {
  @Id private String userUid;

  @ManyToMany
  @JoinTable(name = "enrolled_courses")
  private List<CourseEntity> enrolledCourses = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "fav_courses")
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
}
