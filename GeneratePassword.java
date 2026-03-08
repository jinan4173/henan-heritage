import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Generate password hash
        String password = "admin123";
        String hashedPassword = encoder.encode(password);
        
        System.out.println("Password: " + password);
        System.out.println("Hashed: " + hashedPassword);
        
        // Verify password
        boolean matches = encoder.matches(password, hashedPassword);
        System.out.println("Match: " + matches);
    }
}