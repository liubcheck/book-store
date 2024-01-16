package liubomyr.stepanenko.bookstore.service;

import liubomyr.stepanenko.bookstore.dto.user.UserRegistrationRequestDto;
import liubomyr.stepanenko.bookstore.dto.user.UserResponseDto;
import liubomyr.stepanenko.bookstore.exception.RegistrationException;

public interface AuthenticationService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
