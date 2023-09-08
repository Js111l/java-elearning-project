package org.elearning.project.model;

import java.util.List;

public record Course(
    int id,
    String title,
    String shortDescription,
    String level,
    List<Integer> lessonIds) {}
