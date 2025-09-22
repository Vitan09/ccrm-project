package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Enrollment {
    private final String id;
    private final String studentId;
    private final String courseCode;
    private final LocalDateTime enrolledAt;
    private Integer marks; // 0..100
    private Grade grade;

    public Enrollment(String id, String studentId, String courseCode) {
        this.id = Objects.requireNonNull(id);
        this.studentId = Objects.requireNonNull(studentId);
        this.courseCode = Objects.requireNonNull(courseCode);
        this.enrolledAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) {
        if (marks != null && (marks < 0 || marks > 100)) throw new IllegalArgumentException("Marks must be 0..100");
        this.marks = marks;
        if (marks != null) this.grade = Grade.fromPercentage(marks);
    }

    public Grade getGrade() { return grade; }

    @Override
    public String toString() {
        return String.format("Enrollment[id=%s, student=%s, course=%s, marks=%s, grade=%s]",
                id, studentId, courseCode, marks, grade);
    }
}
