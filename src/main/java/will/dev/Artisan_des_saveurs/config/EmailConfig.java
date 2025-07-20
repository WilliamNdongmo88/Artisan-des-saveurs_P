package will.dev.Artisan_des_saveurs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuration pour l'envoi d'emails
 */
@Configuration
@Slf4j
public class EmailConfig {

    @Value("${spring.mail.host:smtp.gmail.com}")
    private String mailHost;

    @Value("${spring.mail.port:587}")
    private int mailPort;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Value("${spring.mail.password:}")
    private String mailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth:true}")
    private boolean smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:true}")
    private boolean starttlsEnable;

    @Value("${spring.mail.properties.mail.smtp.starttls.required:true}")
    private boolean starttlsRequired;

    @Value("${spring.mail.properties.mail.smtp.ssl.trust:}")
    private String sslTrust;

    /**
     * Configuration du JavaMailSender
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Configuration du serveur SMTP
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        // Propriétés SMTP
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.smtp.starttls.required", starttlsRequired);

        // Configuration SSL/TLS
        if (sslTrust != null && !sslTrust.trim().isEmpty()) {
            props.put("mail.smtp.ssl.trust", sslTrust);
        }

        // Timeout configurations
        props.put("mail.smtp.connectiontimeout", "10000"); // 10 seconds
        props.put("mail.smtp.timeout", "10000"); // 10 seconds
        props.put("mail.smtp.writetimeout", "10000"); // 10 seconds

        // Debug mode (activé en développement)
        props.put("mail.debug", "false");

        // Encoding
        props.put("mail.mime.charset", "UTF-8");

        log.info("Configuration JavaMailSender - Host: {}, Port: {}, Auth: {}, STARTTLS: {}",
                mailHost, mailPort, smtpAuth, starttlsEnable);

        return mailSender;
    }

    /**
     * Vérifie si la configuration email est complète
     */
    public boolean isEmailConfigured() {
        boolean configured = mailHost != null && !mailHost.trim().isEmpty() &&
                mailUsername != null && !mailUsername.trim().isEmpty() &&
                mailPassword != null && !mailPassword.trim().isEmpty();

        if (!configured) {
            log.warn("Configuration email incomplète - Host: {}, Username: {}, Password: {}",
                    mailHost,
                    mailUsername != null && !mailUsername.trim().isEmpty() ? "configuré" : "manquant",
                    mailPassword != null && !mailPassword.trim().isEmpty() ? "configuré" : "manquant");
        }

        return configured;
    }

    /**
     * Obtient les informations de configuration email (sans les mots de passe)
     */
    public EmailConfigInfo getConfigInfo() {
        return EmailConfigInfo.builder()
                .host(mailHost)
                .port(mailPort)
                .username(mailUsername)
                .hasPassword(mailPassword != null && !mailPassword.trim().isEmpty())
                .smtpAuth(smtpAuth)
                .starttlsEnable(starttlsEnable)
                .starttlsRequired(starttlsRequired)
                .sslTrust(sslTrust)
                .configured(isEmailConfigured())
                .build();
    }

    /**
     * Classe pour exposer les informations de configuration sans les mots de passe
     */
    public static class EmailConfigInfo {
        private String host;
        private int port;
        private String username;
        private boolean hasPassword;
        private boolean smtpAuth;
        private boolean starttlsEnable;
        private boolean starttlsRequired;
        private String sslTrust;
        private boolean configured;

        public static EmailConfigInfoBuilder builder() {
            return new EmailConfigInfoBuilder();
        }

        public static class EmailConfigInfoBuilder {
            private String host;
            private int port;
            private String username;
            private boolean hasPassword;
            private boolean smtpAuth;
            private boolean starttlsEnable;
            private boolean starttlsRequired;
            private String sslTrust;
            private boolean configured;

            public EmailConfigInfoBuilder host(String host) {
                this.host = host;
                return this;
            }

            public EmailConfigInfoBuilder port(int port) {
                this.port = port;
                return this;
            }

            public EmailConfigInfoBuilder username(String username) {
                this.username = username;
                return this;
            }

            public EmailConfigInfoBuilder hasPassword(boolean hasPassword) {
                this.hasPassword = hasPassword;
                return this;
            }

            public EmailConfigInfoBuilder smtpAuth(boolean smtpAuth) {
                this.smtpAuth = smtpAuth;
                return this;
            }

            public EmailConfigInfoBuilder starttlsEnable(boolean starttlsEnable) {
                this.starttlsEnable = starttlsEnable;
                return this;
            }

            public EmailConfigInfoBuilder starttlsRequired(boolean starttlsRequired) {
                this.starttlsRequired = starttlsRequired;
                return this;
            }

            public EmailConfigInfoBuilder sslTrust(String sslTrust) {
                this.sslTrust = sslTrust;
                return this;
            }

            public EmailConfigInfoBuilder configured(boolean configured) {
                this.configured = configured;
                return this;
            }

            public EmailConfigInfo build() {
                EmailConfigInfo info = new EmailConfigInfo();
                info.host = this.host;
                info.port = this.port;
                info.username = this.username;
                info.hasPassword = this.hasPassword;
                info.smtpAuth = this.smtpAuth;
                info.starttlsEnable = this.starttlsEnable;
                info.starttlsRequired = this.starttlsRequired;
                info.sslTrust = this.sslTrust;
                info.configured = this.configured;
                return info;
            }
        }

        // Getters
        public String getHost() { return host; }
        public int getPort() { return port; }
        public String getUsername() { return username; }
        public boolean isHasPassword() { return hasPassword; }
        public boolean isSmtpAuth() { return smtpAuth; }
        public boolean isStarttlsEnable() { return starttlsEnable; }
        public boolean isStarttlsRequired() { return starttlsRequired; }
        public String getSslTrust() { return sslTrust; }
        public boolean isConfigured() { return configured; }
    }
}


