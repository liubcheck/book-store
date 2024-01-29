package liubomyr.stepanenko.bookstore.service;

import liubomyr.stepanenko.bookstore.dto.user.UserResponseDto;
import liubomyr.stepanenko.bookstore.dto.user.request.UserRegistrationRequestDto;
import liubomyr.stepanenko.bookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
