package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.entities.LessonEntity;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {
}
