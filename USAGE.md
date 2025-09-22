Usage Guide – CCRM (Campus Course \& Records Manager)

===================================================



After running the program (java -cp out edu.ccrm.cli.CCRMApp), the main menu appears:



=== Main Menu ===

1\. Manage Students

2\. Manage Courses

3\. Enrollment \& Grades

4\. Import/Export Data

5\. Backup \& Show Backup Size

6\. Reports

7\. Exit

Choose:



-------------------------------------------------------

1\. Manage Students

-------------------------------------------------------

\- Add Student -> Enter registration number, name, and email.

\- List Students -> Shows all registered students.

\- Search Students by Name -> Find students by keyword in their name.



-------------------------------------------------------

2\. Manage Courses

-------------------------------------------------------

\- Add Course -> Enter code, title, credits, instructor, semester, and department.

\- List Courses -> Shows all available courses.



-------------------------------------------------------

3\. Enrollment \& Grades

-------------------------------------------------------

\- Enroll Student -> Enter student ID and course code.

\- Record Marks -> Enter marks for a student’s enrollment.

\- Print Transcript (GPA) -> Shows GPA and enrolled courses for a student.



Enrollment checks:

\- Prevents duplicate enrollment

\- Enforces max credit limit (default = 24 credits/semester)



-------------------------------------------------------

4\. Import/Export Data

-------------------------------------------------------

\- Import Students CSV -> Load students from a CSV file.

\- Import Courses CSV -> Load courses from a CSV file.

\- Export Students CSV -> Writes students to data/export/students.csv

\- Export Courses CSV -> Writes courses to data/export/courses.csv



Example students.csv:

id,regNo,name,email

1,REG001,John Doe,john@example.com

2,REG002,Jane Smith,jane@example.com



Example courses.csv:

code,title,credits,instructor,semester,department

CS101,Intro to CS,4,Dr. Allen,FALL,Computer Science

MA201,Calculus II,3,Prof. Lee,SPRING,Mathematics



-------------------------------------------------------

5\. Backup

-------------------------------------------------------

Creates a backup of the export folder and shows its size in bytes.



-------------------------------------------------------

6\. Reports

-------------------------------------------------------

(Currently placeholders)

\- Top students by GPA

\- GPA distribution



-------------------------------------------------------

7\. Exit

-------------------------------------------------------

Exits the application.



-------------------------------------------------------

Tips

-------------------------------------------------------

\- Always export data after adding students/courses.

\- Use backup to save a copy of exported files.

\- GPA is weighted by course credits.



