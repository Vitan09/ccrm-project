package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Student extends Person {
    private String regNo;
    private Status status;
    private LocalDateTime createdDate;
    private final List<Enrollment> enrollments = new ArrayList<>();

    public enum Status { ACTIVE, INACTIVE }

    public Student(String id, String regNo, String fullName, String email, LocalDateTime createdDate) {
        super(Objects.requireNonNull(id), fullName, email);
        this.regNo = regNo;
        this.createdDate = createdDate != null ? createdDate : LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    @Override
    public String getRole() { return "STUDENT"; }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public Status getStatus() { return status; }
    public void deactivate() { this.status = Status.INACTIVE; }
    public void activate() { this.status = Status.ACTIVE; }

    public LocalDateTime getCreatedDate() { return createdDate; }

    public void addEnrollment(Enrollment e) { enrollments.add(e); }
    public void removeEnrollment(Enrollment e) { enrollments.remove(e); }
    public List<Enrollment> getEnrollments() { return Collections.unmodifiableList(enrollments); }

    @Override
    public String toString() {
        return String.format("Student[id=%s, regNo=%s, name=%s, email=%s, status=%s]",
                id, regNo, fullName, email, status);
    }
}
