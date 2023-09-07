package testutil;

import java.util.List;
import org.elearning.project.entities.LessonEntity;
import org.elearning.project.model.CourseItem;

public class TestUtil {
  public static final Integer VALID_ID_2 = 2;
  public static final Integer VALID_ID_1 = 1;
  public static final Integer INVALID_ID = 999;
  public static final List<LessonEntity> LESSON_ENTITIES =
      Data.getCourseList().stream().flatMap(x -> x.getLessons().stream()).toList();
  public static CourseItem COURSE_ITEM_WITH_COURSE_ID_2 = new CourseItem(List.of(VALID_ID_2));
  public static CourseItem COURSE_ITEM_WITH_COURSE_ID_1 = new CourseItem(List.of(VALID_ID_1));
  public static CourseItem COURSE_ITEM_WITH_INVALID_COURSE_ID = new CourseItem(List.of(INVALID_ID));
}
