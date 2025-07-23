package will.dev.Artisan_des_saveurs.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import will.dev.Artisan_des_saveurs.services.WhatsappNotification;

@Configuration
public class WhatsappConfig {

    @Value("${app.whatsapp.enabled}")
    private boolean whatsappEnabled;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String fromNumber;

    @Bean
    public WhatsappNotification whatsappNotification() {
        if (whatsappEnabled) {
            Twilio.init(accountSid, authToken);
            return new WhatsappNotification(fromNumber);
        } else {
            return new WhatsappNotification(null); // mode désactivé
        }
    }
}

