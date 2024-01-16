package liubomyr.stepanenko.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import liubomyr.stepanenko.bookstore.dto.user.UserRegistrationRequestDto;
import liubomyr.stepanenko.bookstore.dto.user.UserResponseDto;
import liubomyr.stepanenko.bookstore.exception.RegistrationException;
import liubomyr.stepanenko.bookstore.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User authentication", description = "Endpoints for authenticating users")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "Register a new user",
            description = "Saves a new user to the database if there is no such user data stored")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return authenticationService.register(request);
    }
}
