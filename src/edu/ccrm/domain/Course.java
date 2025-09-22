package edu.ccrm.domain;

import java.util.Objects;

/** Course with a Builder (static nested class) */
public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final String instructorId; // link by instructor id
    private final Semester semester;
    private final String department;
    private boolean active = true;

    private Course(Builder b) {
        this.code = Objects.requireNonNull(b.code);
        this.title = b.title;
        this.credits = b.credits;
        this.instructorId = b.instructorId;
        this.semester = b.semester;
        this.department = b.department;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructorId() { return instructorId; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public boolean isActive() { return active; }
    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }

    @Override
    public String toString() {
        return String.format("Course[code=%s, title=%s, credits=%d, semester=%s, dept=%s]",
                code, title, credits, semester, department);
    }

    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String instructorId;
        private Semester semester;
        private String department;

        public Builder code(String code) { this.code = code; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder credits(int credits) { this.credits = credits; return this; }
        public Builder instructorId(String instructorId) { this.instructorId = instructorId; return this; }
        public Builder semester(Semester semester) { this.semester = semester; return this; }
        public Builder department(String department) { this.department = department; return this; }
        public Course build() { return new Course(this); }
    }
}
