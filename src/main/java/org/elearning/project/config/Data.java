package org.elearning.project.config;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.elearning.project.entities.CourseEntity;
import org.elearning.project.entities.LessonEntity;
import org.elearning.project.entities.TextArticleEntity;
import org.elearning.project.entities.UserEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Data {
  public static List<CourseEntity> getCourseList() {
    return IntStream.rangeClosed(1, 3)
        .mapToObj(x -> getCourseObject())
        .collect(Collectors.toList());
  }

  public static List<UserEntity> getUserList() {
    return Lists.newArrayList(
        Iterables.concat(
            IntStream.rangeClosed(0, 10).mapToObj(x -> getUser("id" + x)).toList(),
            IntStream.rangeClosed(11, 20)
                .mapToObj(
                    x -> {
                      var user = getUser("id" + x);
                      var course = getCourseObject();
                      course.setId(1);
                      user.setEnrolledCourses(List.of(course));
                      user.setFavCourses(List.of(course, course));
                      return user;
                    })
                .toList()));
  }

  public static LessonEntity getLesson() {
    LessonEntity lesson = new LessonEntity();
    lesson.setTitle("1. Introduction");

    TextArticleEntity articleEntity = new TextArticleEntity();
    articleEntity.setTitle("Definition and scope of [subject]");
    articleEntity.setContent(
        "This course is designed for beginners who want to gain a basic understanding of [subject]. Whether you're interested in [subject] for personal or professional reasons, this course will provide you with a solid foundation to build upon. Through a combination of theoretical concepts, practical examples, and interactive activities, you'll develop a clear understanding of [subject] and its core principles.");
    articleEntity.setLesson(lesson);
    lesson.setArticles(List.of(articleEntity));
    return lesson;
  }

  public static UserEntity getUser(String uid) {
    UserEntity user = new UserEntity();
    user.setUserUid(uid);
    user.setFavCourses(new ArrayList<>());
    user.setEnrolledCourses(new ArrayList<>());
    return user;
  }

  public static CourseEntity getCourseObject() {
    CourseEntity course = new CourseEntity();
    course.setLevel("beginner");
    course.setTitle("Subject for beginners");
    course.setShortDescription(
        "This course is designed for"
            + " beginners who want to gain a basic understanding "
            + "of [subject]. Whether you're interested in [subject] "
            + "for personal or professional reasons, this course will "
            + "provide you with a solid foundation to build upon."
            + " Through a combination of theoretical concepts, practical examples,"
            + " and interactive activities, you'll develop a clear understanding "
            + "of [subject] and its core principles.");
    LessonEntity lesson = new LessonEntity();
    lesson.setTitle("1. Introduction");
    lesson.setCourse(course);

    TextArticleEntity articleEntity = new TextArticleEntity();
    articleEntity.setTitle("Definition and scope of [subject]");
    articleEntity.setContent(
        "This course is designed for beginners who want to gain a basic understanding of [subject]. Whether you're interested in [subject] for personal or professional reasons, this course will provide you with a solid foundation to build upon. Through a combination of theoretical concepts, practical examples, and interactive activities, you'll develop a clear understanding of [subject] and its core principles.");
    articleEntity.setLesson(lesson);
    lesson.setArticles(List.of(articleEntity));

    LessonEntity lesson2 = new LessonEntity();
    lesson2.setTitle("2. More details");
    TextArticleEntity articleEntity2 = new TextArticleEntity();
    articleEntity2.setTitle("More details about subject");
    articleEntity2.setContent(
        "This course is designed for beginners who want to gain a basic understanding of [subject]. Whether you're interested in [subject] for personal or professional reasons, this course will provide you with a solid foundation to build upon. Through a combination of theoretical concepts, practical examples, and interactive activities, you'll develop a clear understanding of [subject] and its core principles.");
    articleEntity2.setLesson(lesson);
    lesson2.setArticles(List.of(articleEntity2));
    lesson2.setCourse(course);
    course.setLessons(List.of(lesson, lesson2));
    course.setEnrolledCourseUsers(new HashSet<>());
    course.setFavCourseUsers(new HashSet<>());
    return course;
  }
}
