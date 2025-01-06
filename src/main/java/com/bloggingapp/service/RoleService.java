package com.bloggingapp.service;

import com.bloggingapp.dto.RoleDto;

public interface RoleService {

    RoleDto createRole (RoleDto roleDto);

    String deleteRole (Integer roleId);
}
