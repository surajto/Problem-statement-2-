import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/hospitaldb";
    private static final String USER = "root";
    private static final String PASS = "suraj12345"; // Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
