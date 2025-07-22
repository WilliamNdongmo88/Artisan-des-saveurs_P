package will.dev.Artisan_des_saveurs;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

/**
 * Classe principale de l'application Spring Boot Contact API
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class ArtisanDesSaveursApplication {

	private final Environment environment;

	public ArtisanDesSaveursApplication(Environment environment) {
		this.environment = environment;
	}

	public static void main(String[] args) {
		String port = System.getenv("PORT");
		if (port != null) {
			System.setProperty("server.port", port);
		}
		// On configure Dotenv pour qu'il charge le fichier .env s'il existe,
		// mais qu'il n'échoue PAS s'il ne le trouve pas.
		// C'est parfait pour passer de l'environnement local à la production.
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing() // L'option magique !
				.load();

//		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(ArtisanDesSaveursApplication.class, args);
	}

	@Value("${DATABASE_URL}")
	private String databaseUrl;

	@PostConstruct
	public void parseDatabaseUrl() {
		try {
			URI uri = new URI(databaseUrl);
			String userInfo = uri.getUserInfo(); // username:password
			String username = userInfo.split(":")[0];
			String password = userInfo.split(":")[1];
			String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();

			System.setProperty("DATABASE_URL", jdbcUrl);
			System.setProperty("DATABASE_USERNAME", username);
			System.setProperty("DATABASE_PASSWORD", password);
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors du parsing de DATABASE_URL", e);
		}
	}


	/**
	 * Événement déclenché quand l'application est prête
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		logApplicationStartup();
	}

	/**
	 * Affiche les informations de démarrage de l'application
	 */
	private void logApplicationStartup() {
		String protocol = "http";
		if (environment.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}

		String serverPort = environment.getProperty("server.port", "8070");
		String contextPath = environment.getProperty("server.servlet.context-path", "");
		String hostAddress = "localhost";

		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("Impossible de déterminer l'adresse IP de l'hôte");
		}

		String[] activeProfiles = environment.getActiveProfiles();
		String profilesInfo = activeProfiles.length > 0 ? String.join(", ", activeProfiles) : "default";

		log.info("\n----------------------------------------------------------\n" +
						"Application '{}' est démarrée avec succès!\n" +
						"Profil(s) actif(s): {}\n" +
						"Accès local:      {}://localhost:{}{}\n" +
						"Accès externe:    {}://{}:{}{}\n" +
						"Documentation:    {}://localhost:{}{}/swagger-ui.html\n" +
						"Actuator:         {}://localhost:{}{}/actuator\n" +
						"----------------------------------------------------------",
				environment.getProperty("spring.application.name", "Contact API"),
				profilesInfo,
				protocol, serverPort, contextPath,
				protocol, hostAddress, serverPort, contextPath,
				protocol, serverPort, contextPath,
				protocol, serverPort, contextPath
		);

		// Informations sur la base de données
//		String datasourceUrl = environment.getProperty("spring.datasource.url");
//		if (datasourceUrl != null) {
//			log.info("Base de données configurée: {}",
//					datasourceUrl.replaceAll("password=[^&;]*", "password=***"));
//		}
//
//		// Informations sur l'email
//		String mailHost = environment.getProperty("spring.mail.host");
//		if (mailHost != null && !mailHost.trim().isEmpty()) {
//			log.info("Configuration email: {}:{}",
//					mailHost,
//					environment.getProperty("spring.mail.port", "587"));
//		} else {
//			log.warn("Configuration email non trouvée");
//		}
//
//		// Informations sur WhatsApp
//		boolean whatsappEnabled = Boolean.parseBoolean(
//				environment.getProperty("app.whatsapp.enabled", "false"));
//		if (whatsappEnabled) {
//			boolean simulationMode = Boolean.parseBoolean(
//					environment.getProperty("app.whatsapp.simulation-mode", "false"));
//			log.info("WhatsApp activé (mode: {})",
//					simulationMode ? "simulation" : "production");
//		} else {
//			log.info("WhatsApp désactivé");
//		}
//
//		// Informations CORS
//		String allowedOrigins = environment.getProperty("app.cors.allowed-origins");
//		if (allowedOrigins != null) {
//			log.info("CORS configuré pour les origines: {}", allowedOrigins);
//		}
	}
}
