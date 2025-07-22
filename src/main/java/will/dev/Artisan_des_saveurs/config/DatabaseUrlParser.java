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

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

            System.setProperty("DATABASE_URL", jdbcUrl);
            System.setProperty("POSTGRES_USER", username);
            System.setProperty("POSTGRES_PASSWORD", password);
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors du parsing de DATABASE_URL", e);
        }
    }
}
