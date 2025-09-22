Campus Course \& Records Manager (CCRM)

=====================================



CCRM is a simple Java console application for managing students, courses, and enrollments.

It supports features like student registration, course management, GPA calculation, CSV import/export, and backup.



-------------------------------------------------------

Project Structure

-------------------------------------------------------

src/

&nbsp;└── edu/ccrm/

&nbsp;     ├── cli/            -> Main CLI entry point (CCRMApp.java)

&nbsp;     ├── config/         -> App configuration (AppConfig.java)

&nbsp;     ├── datastore/      -> In-memory datastore (DataStore.java)

&nbsp;     ├── domain/         -> Domain models (Student, Course, Enrollment, Semester, etc.)

&nbsp;     ├── exceptions/     -> Custom exceptions

&nbsp;     ├── io/             -> Backup \& Import/Export services

&nbsp;     └── service/        -> Business logic services



-------------------------------------------------------

Features

-------------------------------------------------------

\- Add, list, and search students

\- Add and list courses

\- Enroll students in courses with credit limit checks

\- Record marks and calculate GPA

\- Import and export CSV files (students \& courses)

\- Create backup of exported data

\- Basic reporting (placeholders)



-------------------------------------------------------

Requirements

-------------------------------------------------------

\- Java 17+ (recommended)

\- Any IDE (IntelliJ, Eclipse, VS Code) or just javac/java



-------------------------------------------------------

Setup

-------------------------------------------------------

1\. Open terminal in project root.

2\. Compile:

&nbsp;  javac -d out src/edu/ccrm/\*\*/\*.java

3\. Run:

&nbsp;  java -cp out edu.ccrm.cli.CCRMApp



-------------------------------------------------------

Data Folder

-------------------------------------------------------

By default, data and exports are stored in:



project-root/data/

&nbsp;   export/

&nbsp;       students.csv

&nbsp;       courses.csv



-------------------------------------------------------

License

-------------------------------------------------------

This project is for learning/demo purposes only. Use freely.



