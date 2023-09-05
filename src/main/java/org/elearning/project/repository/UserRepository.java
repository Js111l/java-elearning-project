package org.elearning.project.repository;

import org.elearning.project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
  String FAV_QUERY =
      "SELECT id FROM course WHERE id "
          + "in(SELECT fav_courses_id FROM fav_courses"
          + " WHERE fav_course_users_user_uid LIKE :id);";
  String ENROLLED_QUERY =
      "SELECT id FROM course "
          + " WHERE id in(SELECT enrolled_courses_id FROM enrolled_courses"
          + " WHERE enrolled_course_users_user_uid LIKE :id);";

  @Query(value = FAV_QUERY, nativeQuery = true)
  List<Integer> getFavCourses(@Param("id") String id);

  @Query(value = ENROLLED_QUERY, nativeQuery = true)
  List<Integer> getEnrolledCourses(@Param("id") String id);
}
