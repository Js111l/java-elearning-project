package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.entities.CourseEntity;
import project.entities.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    String FAV_QUERY = "SELECT * FROM course " +
            " WHERE id in(SELECT fav_courses_id FROM fav_courses_id" +
            " WHERE fav_course_users_user_uid LIKE CONCAT('%',:id)" +
            " OR fav_course_users_user_uid LIKE CONCAT('%',:id,'%')" +
            " OR fav_course_users_user_uid LIKE CONCAT(:id,'%'));";
    String ENROLLED_QUERY = "SELECT * FROM course " +
            " WHERE id in(SELECT enrolled_courses_id FROM enrolled_courses" +
            " WHERE enrolled_course_users_user_uid LIKE CONCAT('%',:id)" +
            " OR enrolled_course_users_user_uid LIKE CONCAT('%',:id,'%')" +
            " OR enrolled_course_users_user_uid LIKE CONCAT(:id,'%'));";

    @Query(value = FAV_QUERY, nativeQuery = true)
    List<CourseEntity> getFavCourses(@Param("id") String id);

    @Query(value = ENROLLED_QUERY, nativeQuery = true)
    List<CourseEntity> getEnrolledCourses(@Param("id") String id);

}
