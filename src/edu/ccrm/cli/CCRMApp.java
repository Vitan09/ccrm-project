package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.BackupService;
import edu.ccrm.service.ImportExportService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class CCRMApp {
    private static final Scanner SC = new Scanner(System.in);
    private static final StudentService STUDENT_SERVICE = new StudentService();
    private static final CourseService COURSE_SERVICE = new CourseService();
    private static final EnrollmentService ENROLL_SERVICE = new EnrollmentService();
    private static final ImportExportService IO_SERVICE = new ImportExportService();
    private static final AppConfig CFG = AppConfig.getInstance();

    public static void main(String[] args) {
        System.out.println("Welcome to CCRM - Campus Course & Records Manager");
        mainLoop:
        while (true) {
            printMainMenu();
            String choice = SC.nextLine().trim();
            switch (choice) {
                case "1": manageStudents(); break;
                case "2": manageCourses(); break;
                case "3": manageEnrollment(); break;
                case "4": importExportMenu(); break;
                case "5": backupMenu(); break;
                case "6": reportsMenu(); break;
                case "7": System.out.println("Exiting"); break mainLoop;
                default: System.out.println("Invalid option.");
            }
        }
        SC.close();
    }

    private static void printMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Enrollment & Grades");
        System.out.println("4. Import/Export Data");
        System.out.println("5. Backup & Show Backup Size");
        System.out.println("6. Reports");
        System.out.println("7. Exit");
        System.out.print("Choose: ");
    }

    private static void manageStudents() {
        System.out.println("\n-- Manage Students --");
        System.out.println("a. Add Student");
        System.out.println("b. List Students");
        System.out.println("c. Search by Name");
        System.out.println("d. Back");
        System.out.print("Choose: ");
        String c = SC.nextLine().trim();
        switch (c) {
            case "a": addStudent(); break;
            case "b": listStudents(); break;
            case "c": searchStudents(); break;
            case "d": return;
            default: System.out.println("Invalid option.");
        }
    }

    private static void addStudent() {
        System.out.print("RegNo: ");
        String regNo = SC.nextLine().trim();
        System.out.print("Full name: ");
        String name = SC.nextLine().trim();
        System.out.print("Email: ");
        String email = SC.nextLine().trim();
        var s = STUDENT_SERVICE.createStudent(regNo, name, email);
        System.out.println("Created: " + s);
    }

    private static void listStudents() {
        List<Student> list = STUDENT_SERVICE.listAll();
        if (list.isEmpty()) System.out.println("No students.");
        else list.forEach(System.out::println);
    }

    private static void searchStudents() {
        System.out.print("Name contains: ");
        String q = SC.nextLine().trim();
        List<Student> res = STUDENT_SERVICE.searchByName(q);
        res.forEach(System.out::println);
    }

    private static void manageCourses() {
        System.out.println("\n-- Manage Courses --");
        System.out.println("a. Add Course");
        System.out.println("b. List Courses");
        System.out.println("c. Back");
        System.out.print("Choose: ");
        String c = SC.nextLine().trim();
        switch (c) {
            case "a": addCourse(); break;
            case "b": listCourses(); break;
            case "c": return;
            default: System.out.println("Invalid option.");
        }
    }

    private static void addCourse() {
        System.out.print("Code: ");
        String code = SC.nextLine().trim();
        System.out.print("Title: ");
        String title = SC.nextLine().trim();
        System.out.print("Credits: ");
        int credits = Integer.parseInt(SC.nextLine().trim());
        System.out.print("InstructorId: ");
        String instr = SC.nextLine().trim();
        System.out.print("Semester (SPRING/SUMMER/FALL): ");
        Semester sem = Semester.valueOf(SC.nextLine().trim().toUpperCase());
        System.out.print("Department: ");
        String dept = SC.nextLine().trim();
        var c = COURSE_SERVICE.createCourse(code, title, credits, instr, sem, dept);
        System.out.println("Created: " + c);
    }

    private static void listCourses() {
        var list = COURSE_SERVICE.listAll();
        if (list.isEmpty()) System.out.println("No courses.");
        else list.forEach(System.out::println);
    }

    private static void manageEnrollment() {
        System.out.println("\n-- Enrollment & Grades --");
        System.out.println("a. Enroll Student");
        System.out.println("b. Record Marks");
        System.out.println("c. Print Student Transcript (GPA)");
        System.out.println("d. Back");
        System.out.print("Choose: ");
        String c = SC.nextLine().trim();
        switch (c) {
            case "a": enrollStudent(); break;
            case "b": recordMarks(); break;
            case "c": printTranscript(); break;
            case "d": return;
            default: System.out.println("Invalid option.");
        }
    }

    private static void enrollStudent() {
        try {
            System.out.print("StudentId: ");
            String sid = SC.nextLine().trim();
            System.out.print("CourseCode: ");
            String cc = SC.nextLine().trim();
            var enrollment = ENROLL_SERVICE.enroll(sid, cc);
            System.out.println("Enrolled: " + enrollment);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void recordMarks() {
        try {
            System.out.print("EnrollmentId: ");
            String eid = SC.nextLine().trim();
            System.out.print("Marks (0-100): ");
            int marks = Integer.parseInt(SC.nextLine().trim());
            ENROLL_SERVICE.recordMarks(eid, marks);
            System.out.println("Marks recorded.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void printTranscript() {
        System.out.print("StudentId: ");
        String sid = SC.nextLine().trim();
        double gpa = ENROLL_SERVICE.computeGpaForStudent(sid);
        System.out.println("GPA: " + String.format("%.2f", gpa));
        var list = ENROLL_SERVICE.listByStudent(sid);
        if (list.isEmpty()) System.out.println("No enrollments.");
        else list.forEach(System.out::println);
    }

    private static void importExportMenu() {
        System.out.println("\n-- Import/Export --");
        System.out.println("a. Import Students CSV");
        System.out.println("b. Import Courses CSV");
        System.out.println("c. Export Students CSV");
        System.out.println("d. Export Courses CSV");
        System.out.println("e. Back");
        System.out.print("Choose: ");
        String c = SC.nextLine().trim();
        try {
            switch (c) {
                case "a":
                    System.out.print("Path to CSV: ");
                    String p1 = SC.nextLine().trim();
                    IO_SERVICE.importStudentsCsv(Paths.get(p1));
                    System.out.println("Imported students.");
                    break;
                case "b":
                    System.out.print("Path to CSV: ");
                    String p2 = SC.nextLine().trim();
                    IO_SERVICE.importCoursesCsv(Paths.get(p2));
                    System.out.println("Imported courses.");
                    break;
                case "c":
                    Path outS = Paths.get(CFG.getDataFolder()).resolve("export").resolve("students.csv");
                    IO_SERVICE.exportStudentsCsv(outS);
                    System.out.println("Exported students to " + outS);
                    break;
                case "d":
                    Path outC = Paths.get(CFG.getDataFolder()).resolve("export").resolve("courses.csv");
                    IO_SERVICE.exportCoursesCsv(outC);
                    System.out.println("Exported courses to " + outC);
                    break;
                case "e": return;
                default: System.out.println("Invalid option.");
            }
        } catch (IOException ex) {
            System.out.println("IO Error: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void backupMenu() {
        System.out.println("\n-- Backup --");
        try {
            Path export = Paths.get(CFG.getDataFolder()).resolve("export");
            BackupService bs = new BackupService();
            Path backup = bs.backup(export);
            System.out.println("Backup created: " + backup);
            long size = bs.computeRecursiveSize(backup);
            System.out.println("Backup size (bytes): " + size);
        } catch (Exception ex) {
            System.out.println("Backup error: " + ex.getMessage());
        }
    }

    private static void reportsMenu() {
        System.out.println("\n-- Reports --");
        System.out.println("a. Top students by GPA (not implemented - sample)");
        System.out.println("b. GPA distribution (not implemented - sample)");
        System.out.println("c. Back");
        System.out.print("Choose: ");
        String c = SC.nextLine().trim();
        switch (c) {
            case "a": System.out.println("Feature placeholder."); break;
            case "b": System.out.println("Feature placeholder."); break;
            case "c": return;
            default: System.out.println("Invalid option.");
        }
    }
}
