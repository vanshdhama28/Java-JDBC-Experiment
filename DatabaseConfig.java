import java.sql.*;

public class DatabaseConfig {
    static final String URL = "jdbc:mysql://bytexldb.com:5051/db_43zvtt6dj";
    static final String USER = "user_43zvtt6dj";
    static final String PASSWORD = "p43zvtt6dj";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
