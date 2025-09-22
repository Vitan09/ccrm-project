package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.datastore.DataStore;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.config.AppConfig;

import java.util.*;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final DataStore ds = DataStore.getInstance();
    private final AppConfig cfg = AppConfig.getInstance();

    public Enrollment enroll(String studentId, String courseCode) throws DuplicateEnrollmentException {
        Student s = ds.getStudents().get(studentId);
        Course c = ds.getCourses().get(courseCode);
        if (s == null) throw new IllegalArgumentException("Student not found");
        if (c == null) throw new IllegalArgumentException("Course not found");

        // Check duplicate
        boolean already = ds.getEnrollments().values().stream()
                .anyMatch(e -> e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode));
        if (already) throw new DuplicateEnrollmentException("Student already enrolled in course");

        // Check max credits for the semester
        int currentCredits = (int) ds.getEnrollments().values().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .map(e -> ds.getCourses().get(e.getCourseCode()))
                .filter(Objects::nonNull)
                .mapToInt(Course::getCredits)
                .sum();

        if (currentCredits + c.getCredits() > cfg.getMaxCreditsPerSemester()) {
            throw new MaxCreditLimitExceededException("Enrolling exceeds max credits per semester");
        }

        String id = UUID.randomUUID().toString();
        Enrollment enrollment = new Enrollment(id, studentId, courseCode);
        ds.getEnrollments().put(id, enrollment);
        s.addEnrollment(enrollment);
        return enrollment;
    }

    public void recordMarks(String enrollmentId, int marks) {
        Enrollment e = ds.getEnrollments().get(enrollmentId);
        if (e == null) throw new IllegalArgumentException("Enrollment not found");
        e.setMarks(marks);
    }

    public List<Enrollment> listByStudent(String studentId) {
        return ds.getEnrollments().values().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public double computeGpaForStudent(String studentId) {
        List<Enrollment> list = listByStudent(studentId);
        int totalCredits = list.stream()
                .filter(e -> e.getGrade() != null)
                .map(e -> ds.getCourses().get(e.getCourseCode()))
                .filter(Objects::nonNull)
                .mapToInt(Course::getCredits)
                .sum();
        if (totalCredits == 0) return 0.0;
        double weighted = list.stream()
                .filter(e -> e.getGrade() != null)
                .mapToDouble(e -> e.getGrade().getPoints() * ds.getCourses().get(e.getCourseCode()).getCredits())
                .sum();
        return weighted / totalCredits;
    }
}
