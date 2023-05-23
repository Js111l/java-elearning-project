package project.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TextArticle {
    private String title;
    private String subtitle;
    private String content;
}
