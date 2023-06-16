package project;

import org.springframework.stereotype.Component;
import project.entities.CourseEntity;
import project.entities.LessonEntity;
import project.entities.TextArticleEntity;
import project.services.CourseService;
import project.services.LessonService;

import java.util.List;

@Component
public class Saver {
    private final CourseService courseService;
    private final LessonService lessonService;

    public Saver(CourseService courseService, LessonService lessonService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    public void save() {
        CourseEntity course = new CourseEntity();
    course.setTitle("Entrepreneurship and Small Business Management");
        course.setShortDescription("One of the fundamental differences between entrepreneurship and traditional employment is the ability to chart your own course. Entrepreneurs have the freedom to pursue their visions and shape their destinies. They are not bound by the limitations of a corporate structure but instead have the opportunity to build something from the ground up.");
        LessonEntity lesson = new LessonEntity();
        lesson.setTitle("1. Introduction to Entrepreneurship");
        lesson.setCourse(course);

        TextArticleEntity articleEntity = new TextArticleEntity();
        articleEntity.setTitle("Unleashing Your Entrepreneurial Spirit");
    articleEntity.setContent(
        "Welcome to the exciting world of entrepreneurship! In this lesson, we'll dive into what it means to be an entrepreneur and explore the key traits that successful entrepreneurs possess.\n"
            + "\n"
            + "Entrepreneurship is not just about starting a business; it's a mindset that embraces innovation, creativity, and calculated risk-taking. Entrepreneurs are driven by their passion for solving problems and creating value in the marketplace. They possess qualities such as resilience, adaptability, and a relentless drive to achieve their goals.\n"
            + "\n"
            + "One of the fundamental differences between entrepreneurship and traditional employment is the ability to chart your own course. Entrepreneurs have the freedom to pursue their visions and shape their destinies. They are not bound by the limitations of a corporate structure but instead have the opportunity to build something from the ground up.");
        articleEntity.setLesson(lesson);
        lesson.setArticles(List.of(articleEntity));

        LessonEntity lesson2 = new LessonEntity();
        lesson2.setTitle("2. Discovering Diamonds in the Market");
        TextArticleEntity articleEntity2 = new TextArticleEntity();
        articleEntity2.setTitle("Unveiling Business Opportunities");
    articleEntity2.setContent(
        "In the vast landscape of business, opportunities are like hidden gems waiting to be discovered. In this lesson, we will explore the art and science of identifying business opportunities and finding those diamonds in the market.\n"
            + "\n"
            + "Successful entrepreneurs possess a keen eye for recognizing gaps and unmet needs in the marketplace. They have an innate ability to spot trends, understand customer pain points, and envision innovative solutions. This process starts with market research and analysis.\n"
            + "\n"
            + "Market research is a crucial step in identifying business opportunities. It involves gathering data about potential customers, understanding their preferences, and evaluating the competitive landscape. By conducting thorough market research, entrepreneurs gain insights into market demand, customer behavior, and emerging trends.\n"
            + "\n"
            + "Additionally, entrepreneurs must assess their own skills, interests, and resources to identify opportunities that align with their strengths. Finding a niche market or untapped segment can provide a competitive advantage and increase the chances of success.\n"
            + "\n"
            + "During this course, we will dive deeper into various techniques and tools for identifying business opportunities. From conducting customer surveys to analyzing industry reports, we will equip you with the skills to uncover promising opportunities and turn them into thriving ventures.");
        articleEntity2.setLesson(lesson);
        lesson2.setArticles(List.of(articleEntity2));
        lesson2.setCourse(course);
        course.setLessons(List.of(lesson, lesson2));
        this.courseService.saveCourse(course);
    }
}
