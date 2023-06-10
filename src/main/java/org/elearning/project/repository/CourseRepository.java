package org.elearning.project.repository;

import org.elearning.project.entities.CourseEntity;
import org.elearning.project.entities.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
  String QUERY =
      "SELECT * FROM course"
          + " WHERE LOWER(title) LIKE CONCAT('%',LOWER(:name))"
          + " OR LOWER(title)  LIKE CONCAT('%',LOWER(:name),'%')"
          + " OR LOWER(title)  LIKE CONCAT(LOWER(:name),'%');";
  String DELETE_ARTICLE_QUERY = "DELETE FROM text_article WHERE lesson_id=:lessonId";
  String DELETE_LESSON_QUERY = "DELETE FROM lesson WHERE course_id=:courseId AND id=:lessonId";

  String LESSON_QUERY = "SELECT id FROM lesson WHERE course_id=:id";

  @Query(value = QUERY, nativeQuery = true)
  List<CourseEntity> findByName(String name);

  @Modifying
  @Query(value = DELETE_ARTICLE_QUERY, nativeQuery = true)
  void deleteTextArticleByLessonId(int lessonId);

  @Modifying
  @Query(value = DELETE_LESSON_QUERY, nativeQuery = true)
  void deleteLessonInCourse(int courseId, int lessonId);

  @Query(value = LESSON_QUERY, nativeQuery = true)
  List<Integer> getLessonsByCourseId(int id);
}
