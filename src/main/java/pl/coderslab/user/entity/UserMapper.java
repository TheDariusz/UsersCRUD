package pl.coderslab.user.entity;

import pl.coderslab.user.api.request.UserDto;

public class UserMapper {

        public User mapToEntity(UserDto userDto) {
            long id=0;
            try {
                id = Long.parseLong(userDto.getId());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return new User(id,
                    userDto.getUserName(),
                    userDto.getEmail(),
                    userDto.getPassword()
            );
        }
}
