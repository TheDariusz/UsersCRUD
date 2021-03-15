package pl.coderslab.user.entity;

import pl.coderslab.user.api.request.UserDto;

public class UserMapper {

        public User mapToEntity(UserDto userDto) {
            return new User(
                    userDto.getUserName(),
                    userDto.getEmail(),
                    userDto.getPassword()
            );
        }

        public UserDto mapToDto(User user) {
            return new UserDto(
                    user.getUserName(),
                    user.getEmail(),
                    user.getPassword()
            );
        }
}
