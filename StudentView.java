import java.sql.*;
import java.util.Scanner;

public class StudentView {
    private Scanner scanner;
    private StudentController controller;
    
    public StudentView() throws SQLException {
        scanner = new Scanner(System.in);
        controller = new StudentController();
    }
    
    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addStudent(); break;
                case 2: controller.viewAllStudents(); break;
                case 3: updateStudent(); break;
                case 4: deleteStudent(); break;
                case 5: 
                    controller.close();
                    scanner.close();
                    return;
                default: 
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        
        Student student = new Student(id, name, department, marks);
        controller.addStudent(student);
    }
    
    private void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter new Department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter new Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        
        Student student = new Student(id, name, department, marks);
        controller.updateStudent(student);
    }
    
    private void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        controller.deleteStudent(id);
    }
    
    public static void main(String[] args) {
        try {
            new StudentView().displayMenu();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}