package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.entities.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
}
