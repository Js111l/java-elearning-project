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
        course.setLevel("beginner");
        course.setTitle("Subject for beginners");
        course.setShortDescription("This course is designed for" +
                " beginners who want to gain a basic understanding " +
                "of [subject]. Whether you're interested in [subject] " +
                "for personal or professional reasons, this course will " +
                "provide you with a solid foundation to build upon." +
                " Through a combination of theoretical concepts, practical examples," +
                " and interactive activities, you'll develop a clear understanding " +
                "of [subject] and its core principles." +
                "");
        LessonEntity lesson = new LessonEntity();
        lesson.setTitle("1. Introduction");
        lesson.setCourse(course);

        TextArticleEntity articleEntity = new TextArticleEntity();
        articleEntity.setTitle("Definition and scope of [subject]");
        articleEntity.setContent("This course is designed for beginners who want to gain a basic understanding of [subject]. Whether you're interested in [subject] for personal or professional reasons, this course will provide you with a solid foundation to build upon. Through a combination of theoretical concepts, practical examples, and interactive activities, you'll develop a clear understanding of [subject] and its core principles.");
        articleEntity.setLesson(lesson);
        lesson.setArticles(List.of(articleEntity));

        LessonEntity lesson2 = new LessonEntity();
        lesson2.setTitle("2. More details");
        TextArticleEntity articleEntity2 = new TextArticleEntity();
        articleEntity2.setTitle("More details about subject");
        articleEntity2.setContent("This course is designed for beginners who want to gain a basic understanding of [subject]. Whether you're interested in [subject] for personal or professional reasons, this course will provide you with a solid foundation to build upon. Through a combination of theoretical concepts, practical examples, and interactive activities, you'll develop a clear understanding of [subject] and its core principles.");
        articleEntity2.setLesson(lesson);
        lesson2.setArticles(List.of(articleEntity2));
        lesson2.setCourse(course);
        course.setLessons(List.of(lesson, lesson2));
        this.courseService.saveCourse(course);
    }
}
