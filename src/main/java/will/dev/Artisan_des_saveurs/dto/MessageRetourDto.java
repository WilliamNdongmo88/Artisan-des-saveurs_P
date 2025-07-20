package will.dev.Artisan_des_saveurs.dto;

import lombok.Data;

@Data
public class MessageRetourDto {
    private Boolean success;
    private String message;
}
//   success: true,
//   message: 'Votre message a été envoyé avec succès ! Nous vous répondrons dans les plus brefs délais.'