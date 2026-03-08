import java.sql.*;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/heritage_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "741523";
        
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connection successful!");
            
            // Test query
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM heritage_item")) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Total heritage items: " + count);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Database connection failed:");
            e.printStackTrace();
        }
    }
}