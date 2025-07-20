package will.dev.Artisan_des_saveurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import will.dev.Artisan_des_saveurs.dto.ContactRequestDto;
import will.dev.Artisan_des_saveurs.dtoMapper.ContactRequestDtoMapper;
import will.dev.Artisan_des_saveurs.entity.ContactRequest;
import will.dev.Artisan_des_saveurs.repository.ContactRequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact-requests")
@RequiredArgsConstructor
public class ContactRequestController {

    private final ContactRequestRepository contactRequestRepository;

    // GET /contact-requests
    @GetMapping
    public ResponseEntity<List<ContactRequestDto>> getAllRequests() {
        List<ContactRequest> requests = contactRequestRepository.findAll();

        List<ContactRequestDto> dtos = requests.stream()
                .map(ContactRequestDtoMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET /contact-requests/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ContactRequestDto> getById(@PathVariable Long id) {
        return contactRequestRepository.findById(id)
                .map(r -> ResponseEntity.ok(ContactRequestDtoMapper.toDto(r)))
                .orElse(ResponseEntity.notFound().build());
    }
}

