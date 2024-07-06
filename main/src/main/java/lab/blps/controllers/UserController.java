package lab.blps.controllers;

import jakarta.validation.Valid;
import lab.blps.security.dto.request.AmountRequestDto;
import lab.blps.security.dto.response.AmountRequestResponseDto;
import lab.blps.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/amount-request/get")
    public ResponseEntity<?> loadAmountRequest(@Valid @RequestBody AmountRequestDto amountRequestDto) {
        Integer amountRequest = userService.loadAmountRequest(amountRequestDto.getUserId());
        return ResponseEntity.ok(new AmountRequestResponseDto(amountRequestDto.getUserId(), amountRequest));
    }
}
