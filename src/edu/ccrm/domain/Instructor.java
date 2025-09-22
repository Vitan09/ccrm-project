package edu.ccrm.domain;

import java.util.Objects;

public class Instructor extends Person {
    private String department;

    public Instructor(String id, String fullName, String email, String department) {
        super(Objects.requireNonNull(id), fullName, email);
        this.department = department;
    }

    @Override
    public String getRole() { return "INSTRUCTOR"; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return String.format("Instructor[id=%s, name=%s, dept=%s]", id, fullName, department);
    }
}
