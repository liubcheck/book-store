package liubomyr.stepanenko.bookstore.service.impl;

import jakarta.transaction.Transactional;
import liubomyr.stepanenko.bookstore.dto.user.UserResponseDto;
import liubomyr.stepanenko.bookstore.dto.user.request.UserRegistrationRequestDto;
import liubomyr.stepanenko.bookstore.exception.RegistrationException;
import liubomyr.stepanenko.bookstore.mapper.UserMapper;
import liubomyr.stepanenko.bookstore.model.Role;
import liubomyr.stepanenko.bookstore.model.ShoppingCart;
import liubomyr.stepanenko.bookstore.model.User;
import liubomyr.stepanenko.bookstore.model.type.RoleName;
import liubomyr.stepanenko.bookstore.repository.role.RoleRepository;
import liubomyr.stepanenko.bookstore.repository.shoppingcart.ShoppingCartRepository;
import liubomyr.stepanenko.bookstore.repository.user.UserRepository;
import liubomyr.stepanenko.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setShippingAddress(request.getShippingAddress());

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new IllegalStateException("USER role not found"));
        user.getRoles().add(userRole);

        userRepository.save(user);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);

        return userMapper.toDto(user);
    }
}
