package will.dev.Artisan_des_saveurs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import will.dev.Artisan_des_saveurs.dto.MessageRetourDto;
import will.dev.Artisan_des_saveurs.dto.UserDto;
import will.dev.Artisan_des_saveurs.dto.order.OrderDTO;
import will.dev.Artisan_des_saveurs.repository.UserRepository;
import will.dev.Artisan_des_saveurs.services.OrderService;
import will.dev.Artisan_des_saveurs.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("place-order")
    public ResponseEntity<MessageRetourDto> createUser(@RequestBody OrderDTO orderDTO) {
        return this.orderService.sendOrder(orderDTO);
    }
}
