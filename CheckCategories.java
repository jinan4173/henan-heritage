import java.sql.*;

public class CheckCategories {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/heritage_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "741523";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to database successfully!");

            // 查询分类表
            String sql = "SELECT * FROM heritage_category";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                System.out.println("Categories in the database:");
                System.out.println("ID | Name");
                System.out.println("----------------------------");

                while (rs.next()) {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    System.out.println(id + " | " + name);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}