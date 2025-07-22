package will.dev.Artisan_des_saveurs.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class DatabaseUrlParser {

    @Value("${DATABASE_URL}")
    private String databaseUrl;

    @PostConstruct
    public void init() {
        try {
            URI dbUri = new URI(databaseUrl);

            String[] userInfo = dbUri.getUserInfo().split(":");
            String username = userInfo[0];
            String password = userInfo[1];
            String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

            // Injecte dans les variables que Spring Boot attend
            System.setProperty("JDBC_DATABASE_URL", jdbcUrl);
            System.setProperty("JDBC_DATABASE_USERNAME", username);
            System.setProperty("JDBC_DATABASE_PASSWORD", password);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de parsing de DATABASE_URL: " + databaseUrl, e);
        }
    }
}
