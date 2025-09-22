package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.datastore.DataStore;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private final DataStore ds = DataStore.getInstance();

    public Student createStudent(String regNo, String fullName, String email) {
        String id = UUID.randomUUID().toString();
        Student s = new Student(id, regNo, fullName, email, LocalDateTime.now());
        ds.getStudents().put(id, s);
        return s;
    }

    public Optional<Student> findById(String id) {
        return Optional.ofNullable(ds.getStudents().get(id));
    }

    public List<Student> listAll() {
        return new ArrayList<>(ds.getStudents().values());
    }

    public void deactivate(String id) {
        Student s = ds.getStudents().get(id);
        if (s != null) s.deactivate();
    }

    public List<Student> searchByName(String namePart) {
        String lower = namePart == null ? "" : namePart.toLowerCase();
        return ds.getStudents().values().stream()
                .filter(s -> s.getFullName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }
}
