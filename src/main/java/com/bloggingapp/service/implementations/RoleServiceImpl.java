package com.bloggingapp.service.implementations;

import com.bloggingapp.dto.RoleDto;
import com.bloggingapp.entity.RoleEntity;
import com.bloggingapp.exception.CustomServiceException;
import com.bloggingapp.exception.DataNotFoundException;
import com.bloggingapp.repositories.RoleRepo;
import com.bloggingapp.service.RoleService;
import com.bloggingapp.utils.ObjectMapping;
import org.modelmapper.ConfigurationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final ObjectMapping objectMapping;

    public RoleServiceImpl (RoleRepo roleRepo, ObjectMapping objectMapping) {
        this.roleRepo = roleRepo;
        this.objectMapping = objectMapping;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        int roleId = 0;
        if (roleDto == null) {
            throw new IllegalArgumentException("Role data cannot be empty");
        }
        try {
            RoleEntity roleEntity = this.objectMapping.modelMapping(roleDto, RoleEntity.class);
//            roleId = roleEntity.getRoleId();
            return objectMapping.modelMapping(this.roleRepo.save(roleEntity), RoleDto.class);
        } catch (DataAccessException e) {
            System.out.println(e);
            throw new CustomServiceException("Unable to save data for id : " + roleId);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e);
            throw new CustomServiceException("Some data is missing for id : " + roleId);
        } catch (ConfigurationException e) {
            System.out.println(e);
            throw new CustomServiceException("Data could not be mapped for id : " + roleId);
        }
    }

    @Override
    public String deleteRole(Integer roleId) {
        if (roleId <= 0) {
            throw new IllegalArgumentException("Role id is invalid");
        }
        try {
            RoleEntity roleEntity = this.roleRepo.findById(roleId).orElseThrow(() -> new DataNotFoundException(roleId, "Connot find data for id : " + roleId));
            this.roleRepo.delete(roleEntity);
            return "Successfully deleted role";
        } catch (DataNotFoundException e) {
            System.out.println(e);
            throw new CustomServiceException("Role not found for id : " + roleId);
        } catch (DataAccessException e) {
            System.out.println(e);
            throw new CustomServiceException("Could not fetch data for id : " + roleId);
        } catch (ConfigurationException e) {
            System.out.println(e);
            throw new CustomServiceException("Data could not be mapped for id : " + roleId);
        }
    }

    @Override
    public List<RoleDto> getAllRoles () {
        try {
            List<RoleEntity> roleEntities = this.roleRepo.findAll();
            List<RoleDto> roleDtos = roleEntities.stream().map(roleEntity -> objectMapping.modelMapping(roleEntity, RoleDto.class)).collect(Collectors.toList());
            return roleDtos;
        } catch (DataAccessException e) {
            System.out.println(e);
            throw new CustomServiceException("Could not fetch data for roles");
        } catch (ConfigurationException e) {
            System.out.println(e);
            throw new CustomServiceException("Data could not be mapped for roles");
        }
    }
}
