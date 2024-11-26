import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

class Student {
    private int rollNo;
    private String name;
    private double marks;
    private String address;
    private String phoneNumber;
    private String grade;

    public Student(int rollNo, String name, double marks, String address, String phoneNumber) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.grade = calculateGrade(marks);
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String calculateGrade(double marks) {
        if (marks >= 90) {
            return "A";
        } else if (marks >= 80) {
            return "B";
        } else if (marks >= 70) {
            return "C";
        } else if (marks >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public boolean hasPassed() {
        return marks >= 50;
    }

    @Override
    public String toString() {
        return "Roll No: " + rollNo + ", Name: " + name + ", Marks: " + marks + ", Grade: " + grade + ", Address: " + address + ", Phone Number: " + phoneNumber;
    }
}

public class StudentManagementSystem {
    private ArrayList<Student> students;
    private Scanner scanner;

    public StudentManagementSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Welcome to the Student Management System ===");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new student");
            System.out.println("2. View all students");
            System.out.println("3. View student report");
            System.out.println("4. Add multiple students");
            System.out.println("5. Calculate pass/fail status");
            System.out.println("6. Update student information");
            System.out.println("7. Delete a student");
            System.out.println("8. Search student by name");
            System.out.println("9. Sort students by marks");
            System.out.println("10. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> viewStudentReport();
                case 4 -> addMultipleStudents();
                case 5 -> calculatePassFailStatus();
                case 6 -> updateStudentInfo();
                case 7 -> deleteStudent();
                case 8 -> searchStudentByName();
                case 9 -> sortStudentsByMarks();
                case 10 -> {
                    System.out.println("Exiting... Thank you for using the system!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter the name of the student: ");
        String name = scanner.nextLine();
        System.out.print("Enter the marks of the student: ");
        double marks = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter the address of the student: ");
        String address = scanner.nextLine();
        System.out.print("Enter the phone number of the student: ");
        String phoneNumber = scanner.nextLine();

        int rollNo = students.size() + 1;
        students.add(new Student(rollNo, name, marks, address, phoneNumber));
        System.out.println("Student added successfully with Roll No: " + rollNo);
    }

    private void addMultipleStudents() {
        System.out.print("Enter number of students to add: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        for (int i = 0; i < n; i++) {
            addStudent();
        }
    }

    private void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            System.out.println("List of students:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private void viewStudentReport() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            System.out.println("Student Reports:");
            for (Student student : students) {
                System.out.println("\n" + student);
                System.out.println("Pass/Fail: " + (student.hasPassed() ? "Passed" : "Failed"));
            }
        }
    }

    private void calculatePassFailStatus() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            for (Student student : students) {
                System.out.println(student.getName() + " - Status: " + (student.hasPassed() ? "Passed" : "Failed"));
            }
        }
    }

    private void updateStudentInfo() {
        System.out.print("Enter the Roll No of the student to update: ");
        int rollNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = findStudentByRollNo(rollNo);
        if (student != null) {
            System.out.println("Updating information for Roll No: " + rollNo);
            System.out.print("Enter new name (current: " + student.getName() + "): ");
            String name = scanner.nextLine();
            student.setName(name);

            System.out.print("Enter new address (current: " + student.getAddress() + "): ");
            String address = scanner.nextLine();
            student.setAddress(address);

            System.out.print("Enter new phone number (current: " + student.getPhoneNumber() + "): ");
            String phoneNumber = scanner.nextLine();
            student.setPhoneNumber(phoneNumber);

            System.out.println("Student information updated successfully.");
        } else {
            System.out.println("Student with Roll No " + rollNo + " not found.");
        }
    }

    private void deleteStudent() {
        System.out.print("Enter the Roll No of the student to delete: ");
        int rollNo = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = findStudentByRollNo(rollNo);
        if (student != null) {
            students.remove(student);
            System.out.println("Student with Roll No " + rollNo + " has been deleted.");
        } else {
            System.out.println("Student with Roll No " + rollNo + " not found.");
        }
    }

    private void searchStudentByName() {
        System.out.print("Enter the name of the student to search: ");
        String name = scanner.nextLine();

        boolean found = false;
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println(student);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found with the name: " + name);
        }
    }

    private void sortStudentsByMarks() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            Collections.sort(students, Comparator.comparingDouble(Student::getMarks).reversed());
            System.out.println("Students sorted by marks:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private Student findStudentByRollNo(int rollNo) {
        for (Student student : students) {
            if (student.getRollNo() == rollNo) {
                return student;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.start();
    }
}
