package will.dev.Artisan_des_saveurs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import will.dev.Artisan_des_saveurs.entity.ContactRequest;
import will.dev.Artisan_des_saveurs.entity.User;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender javaMailSender;

    public void envoyer(ContactRequest contactRequest) {
        try {
            User user = contactRequest.getUser();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(contactRequest.getSubject());

            String messageBody = "Client : "+user.getFullName()+"\n\n"
                    + "Email : "+user.getEmail()+".\n\n"
                    + "Téléphone: "+user.getPhone()+".\n\n"
                    + contactRequest.getMessage() + "\n\n";


            message.setFrom("no-reply@will.dev");
            message.setTo(user.getEmail());

            message.setText(messageBody);

            javaMailSender.send(message);
        } catch (RuntimeException e) {
            throw new RuntimeException("NOTIFICATION_EMAIL_EXCEPTION: " + e);
        }
    }

}

