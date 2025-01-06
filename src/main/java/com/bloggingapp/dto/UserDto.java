package com.bloggingapp.dto;

import com.bloggingapp.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    @NotEmpty( message = "Name cannot be empty" )
    @Size(min = 3, message = "Name must be 3 characters long")
    private String name;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @ValidPassword
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 15, message = "Password must be min 8 and max 15 char long")
    private String password;
    @NotEmpty(message = "About cannot be empty")
    private String about;
    @NotEmpty
    private List<RoleDto> roleDtos;
}

