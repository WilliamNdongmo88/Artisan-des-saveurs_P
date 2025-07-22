package will.dev.Artisan_des_saveurs.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
public class FlywayConfiguration {

    @Value("${DATABASE_URL}")
    private String databaseUrl;

    @Bean
    @FlywayDataSource
    public DataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(databaseUrl);
        String[] userInfo = dbUri.getUserInfo().split(":");

        String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(userInfo[0]);
        dataSource.setPassword(userInfo[1]);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .load();
    }
}

