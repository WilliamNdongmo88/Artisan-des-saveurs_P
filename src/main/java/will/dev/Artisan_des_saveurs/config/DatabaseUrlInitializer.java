package will.dev.Artisan_des_saveurs.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import java.net.URI;
import java.net.URISyntaxException;

public class DatabaseUrlInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        String dbUrl = System.getenv("DATABASE_URL");

        if (dbUrl != null && dbUrl.startsWith("postgres://")) {
            try {
                URI uri = new URI(dbUrl);
                String[] userInfo = uri.getUserInfo().split(":");

                String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();

                System.setProperty("spring.datasource.url", jdbcUrl);
                System.setProperty("spring.datasource.username", userInfo[0]);
                System.setProperty("spring.datasource.password", userInfo[1]);

            } catch (URISyntaxException e) {
                throw new RuntimeException("Invalid DATABASE_URL", e);
            }
        }
    }
}

