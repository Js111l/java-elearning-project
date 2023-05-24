package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.entities.CourseEntity;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    final String QUERY = "SELECT * FROM Course WHERE title LIKE %? OR title LIKE %?% OR title LIKE ?%";

    @Query(value = QUERY, nativeQuery = true)
    CourseEntity findByName(String name);
}
