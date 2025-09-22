package edu.ccrm.datastore;

import edu.ccrm.domain.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** Simple in-memory datastore (singleton) */
public class DataStore {
    private static DataStore instance;
    private final Map<String, Student> students = new ConcurrentHashMap<>();
    private final Map<String, Instructor> instructors = new ConcurrentHashMap<>();
    private final Map<String, Course> courses = new ConcurrentHashMap<>();
    private final Map<String, Enrollment> enrollments = new ConcurrentHashMap<>();

    private DataStore() {}

    public static synchronized DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    public Map<String, Student> getStudents() { return students; }
    public Map<String, Instructor> getInstructors() { return instructors; }
    public Map<String, Course> getCourses() { return courses; }
    public Map<String, Enrollment> getEnrollments() { return enrollments; }
}
