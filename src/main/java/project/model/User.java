package project.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String userUid;
    private List<Course> coursesList = new ArrayList<>();

    public User(String userUid) {
        this.userUid = userUid;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public List<Course> getCoursesList() {
        return new ArrayList<>(coursesList);
    }
}
