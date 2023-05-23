package project.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class Course {
    private String title;
    private String shortDescription;
    private String level;
    private List<Lesson> lessons;

}
