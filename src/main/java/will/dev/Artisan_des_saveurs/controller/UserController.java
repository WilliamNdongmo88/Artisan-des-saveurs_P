package will.dev.Artisan_des_saveurs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import will.dev.Artisan_des_saveurs.dto.MessageRetourDto;
import will.dev.Artisan_des_saveurs.dto.UserDto;
import will.dev.Artisan_des_saveurs.dtoMapper.UserDtoMapper;
import will.dev.Artisan_des_saveurs.entity.User;
import will.dev.Artisan_des_saveurs.repository.UserRepository;
import will.dev.Artisan_des_saveurs.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("place-order")
    public ResponseEntity<MessageRetourDto> createUser(@RequestBody @Valid UserDto userDto) {
        return this.userService.createUser(userDto);
    }


    // GET /users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDto> userDtos = users.stream()
                .map(UserDtoMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(UserDtoMapper.toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
