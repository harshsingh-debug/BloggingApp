package com.bloggingapp.controller;

import com.bloggingapp.dto.UserDto;
import com.bloggingapp.dto.UserRoleDto;
import com.bloggingapp.service.UserService;
import com.bloggingapp.service.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/user"})
public class UserController {
    private UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping({"/createUser"})
    public ResponseEntity<UserDto> createNewUser(@RequestBody @Valid UserDto userDto) {
        UserDto userDtoResponse = this.userService.createNewUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoResponse);
    }

    @PutMapping({"/updateUser/{id}"})
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Integer userId) {
        UserDto userDtoResponse = this.userService.updateUser(userDto, userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoResponse);
    }

    @GetMapping({"/getUser/{id}"})
    public ResponseEntity getUserById(@PathVariable("id") Integer userId) {
        UserDto userDtoResponse = this.userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoResponse);
    }

    @GetMapping({"/getUser"})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoResponse = this.userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userDtoResponse);
    }

    @DeleteMapping({"/deleteUser/{id}"})
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer userId) {
        String response = this.userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/updateUser/role/{userId}")
    public ResponseEntity<UserDto> updateRoleForUser (@PathVariable("userId") Integer userId, @RequestBody UserRoleDto userRoleDto) {
        UserDto userDto = this.userService.updateUserRole(userId, userRoleDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
