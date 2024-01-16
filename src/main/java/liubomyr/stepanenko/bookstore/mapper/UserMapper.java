package liubomyr.stepanenko.bookstore.mapper;

import liubomyr.stepanenko.bookstore.config.MapperConfig;
import liubomyr.stepanenko.bookstore.dto.user.UserRegistrationRequestDto;
import liubomyr.stepanenko.bookstore.dto.user.UserResponseDto;
import liubomyr.stepanenko.bookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto dto);
}
