package will.dev.Artisan_des_saveurs.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import will.dev.Artisan_des_saveurs.config.TwilioProperties;
import will.dev.Artisan_des_saveurs.entity.ContactRequest;
import will.dev.Artisan_des_saveurs.entity.User;
import com.twilio.type.PhoneNumber;

@Service
@RequiredArgsConstructor
public class WhatsappNotificationP {
    private final TwilioProperties twilioProps;

    public void sendWhatsappMessage(User user, String to, ContactRequest contactRequest, Boolean isFromCart) {
        try {
            Twilio.init(twilioProps.getAccountSid(), twilioProps.getAuthToken());

            String messages = isFromCart ? contactRequest.getMessage() :
                    "Client : " + user.getFullName() + "\n"
                            + "Email : " + user.getEmail() + ".\n"
                            + "Téléphone: " + user.getPhone() + ".\n\n"
                            + "Sujet: " + contactRequest.getSubject() + ".\n\n"
                            + contactRequest.getMessage() + "\n\n";

            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + to),
                    new PhoneNumber("whatsapp:" + twilioProps.getWhatsappFrom()),
                    messages
            ).create();

            System.out.println("Message SID: " + message.getSid());
        } catch (RuntimeException e) {
            throw new RuntimeException("NOTIFICATION_WHATSAPP_EXCEPTION: " + e);
        }
    }
}
