package edu.ccrm.domain;

import java.util.Objects;

/** Abstract Person class demonstrating abstraction and inheritance */
public abstract class Person {
    protected final String id; // immutable identifier
    protected String fullName;
    protected String email;

    protected Person(String id, String fullName, String email) {
        assert id != null : "id must be non-null";
        this.id = Objects.requireNonNull(id);
        this.fullName = fullName;
        this.email = email;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public abstract String getRole();

    @Override
    public String toString() {
        return String.format("%s (%s)", fullName, id);
    }
}
