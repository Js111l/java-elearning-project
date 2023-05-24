package project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseItem {
    private String userId;
    private Integer courseId;

    public CourseItem(String userId, Integer courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }
}
