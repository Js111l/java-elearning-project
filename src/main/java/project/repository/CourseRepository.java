package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.entities.CourseEntity;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    String QUERY = "SELECT * FROM course" +
            " WHERE title LIKE CONCAT('%',:name)" +
            " OR title LIKE CONCAT('%',:name,'%')" +
            " OR title LIKE CONCAT(:name,'%');";

    @Query(value = QUERY, nativeQuery = true)
    List<CourseEntity> findByName(@Param("name") String name);
}
