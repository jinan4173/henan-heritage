import java.sql.*;

public class CheckHeritageResources {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/heritage_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "741523";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, title, coverImage, videoUrl FROM heritage_item")) {
            
            int totalProjects = 0;
            int missingCoverImage = 0;
            int missingVideoUrl = 0;
            int missingBoth = 0;
            
            System.out.println("==============================================");
            System.out.println("Henan Heritage Resources Status Analysis Report");
            System.out.println("==============================================");
            
            while (rs.next()) {
                totalProjects++;
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String coverImage = rs.getString("coverImage");
                String videoUrl = rs.getString("videoUrl");
                
                boolean hasCoverImage = coverImage != null && !coverImage.isEmpty();
                boolean hasVideoUrl = videoUrl != null && !videoUrl.isEmpty();
                
                if (!hasCoverImage && !hasVideoUrl) {
                    missingBoth++;
                    System.out.println("Project ID: " + id + " | Project Name: " + title + " | Status: Missing cover image and video");
                } else if (!hasCoverImage) {
                    missingCoverImage++;
                    System.out.println("Project ID: " + id + " | Project Name: " + title + " | Status: Missing cover image");
                } else if (!hasVideoUrl) {
                    missingVideoUrl++;
                    System.out.println("Project ID: " + id + " | Project Name: " + title + " | Status: Missing video");
                }
            }
            
            System.out.println("==============================================");
            System.out.println("Statistics:");
            System.out.println("Total projects: " + totalProjects);
            System.out.println("Projects missing cover image: " + (missingCoverImage + missingBoth));
            System.out.println("Projects missing video: " + (missingVideoUrl + missingBoth));
            System.out.println("Projects missing both cover image and video: " + missingBoth);
            System.out.println("Total projects needing resource补充: " + (missingCoverImage + missingVideoUrl + missingBoth));
            System.out.println("==============================================");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}