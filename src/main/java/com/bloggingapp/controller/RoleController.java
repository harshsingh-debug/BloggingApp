package com.bloggingapp.controller;

import com.bloggingapp.dto.RoleDto;
import com.bloggingapp.service.RoleService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController (RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping ("/createRole")
    public ResponseEntity<RoleDto> createRole (@RequestBody RoleDto roleDto) {
        RoleDto roleDtoResponse = this.roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleDtoResponse);
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<String> deleteRole (@PathVariable("id") Integer roleId) {
        String response = this.roleService.deleteRole(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getRoles")
    public ResponseEntity<List<RoleDto>> getAllRoles () {
        List<RoleDto> roleDtos = this.roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(roleDtos);
    }
}
