import java.sql.*;

public class StudentController {
    private Connection conn;
    
    public StudentController() throws SQLException {
        conn = DatabaseConfig.getConnection();
    }
    
    public void addStudent(Student student) {
        try {
            String query = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            pstmt.setDouble(4, student.getMarks());
            
            pstmt.executeUpdate();
            System.out.println("Student added successfully!");
            
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }
    
    public void viewAllStudents() {
        try {
            String query = "SELECT * FROM Student";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("\n=== Student List ===");
            System.out.printf("%-10s %-20s %-20s %-10s%n", "ID", "Name", "Department", "Marks");
            System.out.println("----------------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-20s %-10.2f%n",
                    rs.getInt("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Marks"));
            }
            
        } catch (SQLException e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }
    
    public void updateStudent(Student student) {
        try {
            String query = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setDouble(3, student.getMarks());
            pstmt.setInt(4, student.getStudentID());
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found!");
            }
            
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }
    
    public void deleteStudent(int studentID) {
        try {
            String query = "DELETE FROM Student WHERE StudentID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentID);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }
            
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
    
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
