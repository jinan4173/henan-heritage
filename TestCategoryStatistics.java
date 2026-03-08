import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestCategoryStatistics {
    public static void main(String[] args) {
        try {
            // 创建URL对象
            URL url = new URL("http://localhost:8083/api/statistics/category");
            
            // 打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            
            // 获取响应代码
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            
            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            // 打印响应内容
            System.out.println("Response: " + response.toString());
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}