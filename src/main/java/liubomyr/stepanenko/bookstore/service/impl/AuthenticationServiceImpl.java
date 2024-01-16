package liubomyr.stepanenko.bookstore.service.impl;

import liubomyr.stepanenko.bookstore.dto.user.UserRegistrationRequestDto;
import liubomyr.stepanenko.bookstore.dto.user.UserResponseDto;
import liubomyr.stepanenko.bookstore.exception.RegistrationException;
import liubomyr.stepanenko.bookstore.mapper.UserMapper;
import liubomyr.stepanenko.bookstore.model.User;
import liubomyr.stepanenko.bookstore.repository.user.UserRepository;
import liubomyr.stepanenko.bookstore.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException(
                    String.format("The user with the email %s is already registered",
                            request.getEmail()));
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setShippingAddress(request.getShippingAddress());
        return userMapper.toDto(userRepository.save(user));
    }
}
