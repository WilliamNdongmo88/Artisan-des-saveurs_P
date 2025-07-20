package will.dev.Artisan_des_saveurs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration pour l'intégration WhatsApp
 */
@Configuration
@Slf4j
public class WhatsAppConfig {

    @Value("${app.whatsapp.enabled:true}")
    private boolean whatsappEnabled;

    @Value("${app.whatsapp.api-key:}")
    private String whatsappApiKey;

    @Value("${app.whatsapp.api-url:https://api.whatsapp.com/send}")
    private String whatsappApiUrl;

    @Value("${app.whatsapp.simulation-mode:false}")
    private boolean simulationMode;

    @Value("${app.company.whatsapp.number:+23059221613}")
    private String companyWhatsAppNumber;

    @Value("${app.company.name:Artisan des Saveurs}")
    private String companyName;

    /**
     * Bean RestTemplate pour les appels API WhatsApp
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Vérifie si WhatsApp est configuré correctement
     */
    public boolean isWhatsAppConfigured() {
        if (!whatsappEnabled) {
            log.info("WhatsApp désactivé dans la configuration");
            return false;
        }

        if (simulationMode) {
            log.info("WhatsApp en mode simulation");
            return true;
        }

        boolean hasApiKey = whatsappApiKey != null && !whatsappApiKey.trim().isEmpty();
        boolean hasApiUrl = whatsappApiUrl != null && !whatsappApiUrl.trim().isEmpty();
        boolean hasCompanyNumber = companyWhatsAppNumber != null && !companyWhatsAppNumber.trim().isEmpty();

        boolean configured = hasApiKey && hasApiUrl && hasCompanyNumber;

        if (!configured) {
            log.warn("Configuration WhatsApp incomplète - API Key: {}, API URL: {}, Company Number: {}",
                    hasApiKey ? "configuré" : "manquant",
                    hasApiUrl ? "configuré" : "manquant",
                    hasCompanyNumber ? "configuré" : "manquant");
        } else {
            log.info("Configuration WhatsApp complète");
        }

        return configured;
    }

    /**
     * Obtient les informations de configuration WhatsApp (sans les clés API)
     */
    public WhatsAppConfigInfo getConfigInfo() {
        return WhatsAppConfigInfo.builder()
                .enabled(whatsappEnabled)
                .hasApiKey(whatsappApiKey != null && !whatsappApiKey.trim().isEmpty())
                .apiUrl(whatsappApiUrl)
                .simulationMode(simulationMode)
                .companyNumber(companyWhatsAppNumber)
                .companyName(companyName)
                .configured(isWhatsAppConfigured())
                .build();
    }

    /**
     * Classe pour exposer les informations de configuration sans les clés API
     */
    public static class WhatsAppConfigInfo {
        private boolean enabled;
        private boolean hasApiKey;
        private String apiUrl;
        private boolean simulationMode;
        private String companyNumber;
        private String companyName;
        private boolean configured;

        public static WhatsAppConfigInfoBuilder builder() {
            return new WhatsAppConfigInfoBuilder();
        }

        public static class WhatsAppConfigInfoBuilder {
            private boolean enabled;
            private boolean hasApiKey;
            private String apiUrl;
            private boolean simulationMode;
            private String companyNumber;
            private String companyName;
            private boolean configured;

            public WhatsAppConfigInfoBuilder enabled(boolean enabled) {
                this.enabled = enabled;
                return this;
            }

            public WhatsAppConfigInfoBuilder hasApiKey(boolean hasApiKey) {
                this.hasApiKey = hasApiKey;
                return this;
            }

            public WhatsAppConfigInfoBuilder apiUrl(String apiUrl) {
                this.apiUrl = apiUrl;
                return this;
            }

            public WhatsAppConfigInfoBuilder simulationMode(boolean simulationMode) {
                this.simulationMode = simulationMode;
                return this;
            }

            public WhatsAppConfigInfoBuilder companyNumber(String companyNumber) {
                this.companyNumber = companyNumber;
                return this;
            }

            public WhatsAppConfigInfoBuilder companyName(String companyName) {
                this.companyName = companyName;
                return this;
            }

            public WhatsAppConfigInfoBuilder configured(boolean configured) {
                this.configured = configured;
                return this;
            }

            public WhatsAppConfigInfo build() {
                WhatsAppConfigInfo info = new WhatsAppConfigInfo();
                info.enabled = this.enabled;
                info.hasApiKey = this.hasApiKey;
                info.apiUrl = this.apiUrl;
                info.simulationMode = this.simulationMode;
                info.companyNumber = this.companyNumber;
                info.companyName = this.companyName;
                info.configured = this.configured;
                return info;
            }
        }

        // Getters
        public boolean isEnabled() { return enabled; }
        public boolean isHasApiKey() { return hasApiKey; }
        public String getApiUrl() { return apiUrl; }
        public boolean isSimulationMode() { return simulationMode; }
        public String getCompanyNumber() { return companyNumber; }
        public String getCompanyName() { return companyName; }
        public boolean isConfigured() { return configured; }
    }

    // Getters pour les valeurs de configuration
    public boolean isWhatsappEnabled() {
        return whatsappEnabled;
    }

    public String getWhatsappApiKey() {
        return whatsappApiKey;
    }

    public String getWhatsappApiUrl() {
        return whatsappApiUrl;
    }

    public boolean isSimulationMode() {
        return simulationMode;
    }

    public String getCompanyWhatsAppNumber() {
        return companyWhatsAppNumber;
    }

    public String getCompanyName() {
        return companyName;
    }
}


