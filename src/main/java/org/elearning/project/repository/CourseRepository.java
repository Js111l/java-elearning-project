package org.elearning.project.repository;

import org.elearning.project.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    String QUERY = "SELECT * FROM course" +
            " WHERE LOWER(title) LIKE CONCAT('%',LOWER(:name))" +
            " OR LOWER(title)  LIKE CONCAT('%',LOWER(:name),'%')" +
            " OR LOWER(title)  LIKE CONCAT(LOWER(:name),'%');";

    @Query(value = QUERY, nativeQuery = true)
    List<CourseEntity> findByName(@Param("name") String name);
}
