package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.datastore.DataStore;

import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private final DataStore ds = DataStore.getInstance();

    public Course createCourse(String code, String title, int credits, String instructorId, Semester semester, String department) {
        Course c = new Course.Builder()
                .code(code)
                .title(title)
                .credits(credits)
                .instructorId(instructorId)
                .semester(semester)
                .department(department)
                .build();
        ds.getCourses().put(code, c);
        return c;
    }

    public Optional<Course> findByCode(String code) {
        return Optional.ofNullable(ds.getCourses().get(code));
    }

    public List<Course> listAll() {
        return new ArrayList<>(ds.getCourses().values());
    }

    public List<Course> filterByInstructor(String instructorId) {
        return ds.getCourses().values().stream()
                .filter(c -> instructorId != null && instructorId.equals(c.getInstructorId()))
                .collect(Collectors.toList());
    }

    public List<Course> filterByDepartment(String department) {
        String d = department == null ? "" : department;
        return ds.getCourses().values().stream()
                .filter(c -> d.equals(c.getDepartment()))
                .collect(Collectors.toList());
    }

    public List<Course> filterBySemester(Semester semester) {
        return ds.getCourses().values().stream()
                .filter(c -> semester == c.getSemester())
                .collect(Collectors.toList());
    }
}
