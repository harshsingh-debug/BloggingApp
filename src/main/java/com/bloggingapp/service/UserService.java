package com.bloggingapp.service;

import com.bloggingapp.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto createNewUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    String deleteUser(Integer userId);
}
