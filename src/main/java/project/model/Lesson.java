package project.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class Lesson {
    private String title;
    private List<TextArticle>  articles;
}
