package pl.coderslab.user.entity;

import pl.coderslab.user.api.request.UserDto;

import java.util.Optional;

public class UserMapper {

    public User mapToEntity(UserDto userDto) {
        Optional<String> optionalId = Optional.ofNullable(userDto.getId());
        return optionalId
                .map(Long::parseLong)
                .map(id -> new User(id, userDto.getUserName(),
                        userDto.getEmail(),
                        userDto.getPassword()))
                .orElse(new User(
                        userDto.getUserName(),
                        userDto.getEmail(),
                        userDto.getPassword()
                ));
    }
}
