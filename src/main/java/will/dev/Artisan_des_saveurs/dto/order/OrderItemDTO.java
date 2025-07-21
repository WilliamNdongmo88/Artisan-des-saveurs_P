package will.dev.Artisan_des_saveurs.dto.order;

import lombok.Data;

@Data
public class OrderItemDTO {
    private ProductDTO product;
    private int quantity;

    // Getters et setters
}

