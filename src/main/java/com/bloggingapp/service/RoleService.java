package com.bloggingapp.service;

import com.bloggingapp.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto createRole (RoleDto roleDto);

    String deleteRole (Integer roleId);

    List<RoleDto> getAllRoles ();
}
