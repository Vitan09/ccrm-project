package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Semester;
import edu.ccrm.datastore.DataStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ImportExportService {
    private final DataStore ds = DataStore.getInstance();

    public void importStudentsCsv(Path csvPath) throws IOException {
        try (var stream = Files.lines(csvPath)) {
            List<String> lines = stream.collect(Collectors.toList());
            if (lines.size() <= 1) return; // no data
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;
                String[] cols = line.split(",");
                // expected: id,regNo,fullName,email,status,createdDate
                String id = cols[0].trim();
                String regNo = cols[1].trim();
                String fullName = cols[2].trim();
                String email = cols[3].trim();
                LocalDateTime created = LocalDateTime.parse(cols[5].trim());
                Student s = new Student(id, regNo, fullName, email, created);
                ds.getStudents().put(id, s);
            }
        }
    }

    public void importCoursesCsv(Path csvPath) throws IOException {
        try (var stream = Files.lines(csvPath)) {
            List<String> lines = stream.collect(Collectors.toList());
            if (lines.size() <= 1) return;
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;
                String[] cols = line.split(",");
                // expected: code,title,credits,instructor,semester,department
                String code = cols[0].trim();
                String title = cols[1].trim();
                int credits = Integer.parseInt(cols[2].trim());
                String instructorId = cols[3].trim();
                Semester sem = Semester.valueOf(cols[4].trim().toUpperCase());
                String dept = cols[5].trim();
                Course c = new Course.Builder()
                        .code(code).title(title).credits(credits).instructorId(instructorId)
                        .semester(sem).department(dept).build();
                ds.getCourses().put(code, c);
            }
        }
    }

    public void exportStudentsCsv(Path out) throws IOException {
        String header = "id,regNo,fullName,email,status,createdDate";
        List<String> lines = ds.getStudents().values().stream()
                .map(s -> String.join(",",
                        s.getId(), s.getRegNo(), s.getFullName(), s.getEmail(), s.getStatus().name(), s.getCreatedDate().toString()))
                .collect(Collectors.toList());
        lines.add(0, header);
        Files.createDirectories(out.getParent());
        Files.write(out, lines);
    }

    public void exportCoursesCsv(Path out) throws IOException {
        String header = "code,title,credits,instructor,semester,department";
        List<String> lines = ds.getCourses().values().stream()
                .map(c -> String.join(",",
                        c.getCode(), c.getTitle(), String.valueOf(c.getCredits()), c.getInstructorId(), c.getSemester().name(), c.getDepartment()))
                .collect(Collectors.toList());
        lines.add(0, header);
        Files.createDirectories(out.getParent());
        Files.write(out, lines);
    }
}
