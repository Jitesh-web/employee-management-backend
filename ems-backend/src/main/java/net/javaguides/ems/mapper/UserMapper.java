package net.javaguides.ems.mapper;

import net.javaguides.ems.dto.RegisterRequest;
import net.javaguides.ems.entity.User;

public class UserMapper {

    public static User mapToUser(RegisterRequest request) {

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // Password will be encrypted later
        user.setPassword(request.getPassword());

        return user;
    }
}
