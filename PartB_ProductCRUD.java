import java.sql.*;
import java.util.Scanner;

public class PartB_ProductCRUD {
    private Connection conn;
    private Scanner scanner;
    
    public PartB_ProductCRUD() {
        scanner = new Scanner(System.in);
    }
    
    public void start() {
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);
            
            while (true) {
                System.out.println("\n=== Product Management System ===");
                System.out.println("1. Create Product");
                System.out.println("2. Read All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1: createProduct(); break;
                    case 2: readProducts(); break;
                    case 3: updateProduct(); break;
                    case 4: deleteProduct(); break;
                    case 5: 
                        conn.close();
                        scanner.close();
                        return;
                    default: 
                        System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void createProduct() {
        try {
            System.out.print("Enter Product ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();
            
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            
            String query = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);
            
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product created successfully!");
            
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Transaction rolled back: " + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("Rollback error: " + ex.getMessage());
            }
        }
    }
    
    private void readProducts() {
        try {
            String query = "SELECT * FROM Product";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("\n=== Product List ===");
            System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Quantity");
            System.out.println("--------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-10.2f %-10d%n",
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getDouble("Price"),
                    rs.getInt("Quantity"));
            }
            
        } catch (SQLException e) {
            System.out.println("Error reading products: " + e.getMessage());
        }
    }
    
    private void updateProduct() {
        try {
            System.out.print("Enter Product ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter new Product Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter new Price: ");
            double price = scanner.nextDouble();
            
            System.out.print("Enter new Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            
            String query = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, id);
            
            int rows = pstmt.executeUpdate();
            conn.commit();
            
            if (rows > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("Product not found!");
            }
            
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Transaction rolled back: " + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("Rollback error: " + ex.getMessage());
            }
        }
    }
    
    private void deleteProduct() {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            String query = "DELETE FROM Product WHERE ProductID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            
            int rows = pstmt.executeUpdate();
            conn.commit();
            
            if (rows > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Product not found!");
            }
            
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Transaction rolled back: " + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("Rollback error: " + ex.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        new PartB_ProductCRUD().start();
    }
}
