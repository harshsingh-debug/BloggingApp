package com.bloggingapp.service.implementations;

import com.bloggingapp.dto.UserDto;
import com.bloggingapp.entity.RoleEntity;
import com.bloggingapp.entity.UserEntity;
import com.bloggingapp.exception.CustomServiceException;
import com.bloggingapp.exception.DataNotFoundException;
import com.bloggingapp.repositories.UserRepo;
import com.bloggingapp.service.UserService;
import com.bloggingapp.utils.ObjectMapping;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ConfigurationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ObjectMapping objectMapping;

    UserServiceImpl(UserRepo userRepo, ObjectMapping objectMapping) {
        this.userRepo = userRepo;
        this.objectMapping = objectMapping;
    }

    public UserDto createNewUser(UserDto userDto) {
        Integer userId = 0;
        if (userDto == null) {
            throw new IllegalArgumentException("Empty user data");
        } else {
            try {
                UserEntity userEntity = this.objectMapping.modelMapping(userDto, UserEntity.class);
                userId = userEntity.getId();
                return this.objectMapping.modelMapping(this.userRepo.save(userEntity), UserDto.class);
            } catch (DataAccessException var4) {
                System.out.println(var4);
                throw new CustomServiceException("Unable to save data for id : " + userId);
            } catch (NullPointerException | IllegalArgumentException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Some data is missing for id : " + userId);
            } catch (ConfigurationException var6) {
                System.out.println(var6);
                throw new CustomServiceException("Data could not be mapped for id : " + userId);
            }
        }
    }

    public UserDto updateUser(UserDto userDto, Integer userId) {
        if (userDto == null) {
            throw new IllegalArgumentException("Empty user data");
        } else {
            try {
                UserEntity userEntity = this.userRepo.findById(userId).orElseThrow(() -> new DataNotFoundException(userId, "User does not exist"));
                if (userDto.getName() != null) {
                    userEntity.setName(userDto.getName());
                }

                if (userDto.getPassword() != null) {
                    userEntity.setPassword(userDto.getPassword());
                }

                if (userDto.getEmail() != null) {
                    userEntity.setEmail(userDto.getEmail());
                }

                if (userDto.getAbout() != null) {
                    userEntity.setAbout(userDto.getAbout());
                }

//                if (!userDto.getRoleDtos().isEmpty()) {
//                    userDto.getRoleDtos().stream().forEach(roleDto -> {
//                        RoleEntity roleEntity = objectMapping.modelMapping(roleDto, RoleEntity.class);
//                        userEntity.getRoles().add(roleEntity);
//                    });
//                }

                return this.objectMapping.modelMapping(this.userRepo.save(userEntity), UserDto.class);
            } catch (DataAccessException var4) {
                System.out.println(var4);
                throw new CustomServiceException("Could not fetch data for id : " + userId);
            } catch (DataNotFoundException var5) {
                System.out.println(var5);
                throw new CustomServiceException("User does not exist for id : " + userId);
            } catch (ConfigurationException var6) {
                System.out.println(var6);
                throw new CustomServiceException("Data could not be mapped for id : " + userId);
            }
        }
    }

    public UserDto getUserById(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id can't be empty");
        } else {
            try {
                UserEntity userEntity = this.userRepo.findById(userId).orElseThrow(() -> new DataNotFoundException(userId, "User does not exist"));
                return this.objectMapping.modelMapping(userEntity, UserDto.class);
            } catch (DataNotFoundException var3) {
                System.out.println(var3);
                throw new CustomServiceException("User does now exist for id : " + userId);
            } catch (DataAccessException var4) {
                System.out.println(var4);
                throw new CustomServiceException("Could not fetch data for id : " + userId);
            } catch (ConfigurationException var5) {
                System.out.println(var5);
                throw new CustomServiceException("Data could not be mapped for id : " + userId);
            } catch (Exception var6) {
                System.out.println(var6);
                throw new CustomServiceException("Some exception occured for id : " + userId);
            }
        }
    }

    public List<UserDto> getAllUsers() {
        try {
            List<UserEntity> userEntities = this.userRepo.findAll();
            if (userEntities != null && userEntities.size() != 0) {
                List<UserDto> userDtoList = userEntities.stream().map((userEntity) -> {
                    return this.objectMapping.modelMapping(userEntity, UserDto.class);
                }).collect(Collectors.toList());
                return userDtoList;
            } else {
                return Collections.emptyList();
            }
        } catch (DataAccessException var3) {
            System.out.println(var3);
            throw new CustomServiceException("Could not fetch data for users");
        } catch (ConfigurationException var4) {
            System.out.println(var4);
            throw new CustomServiceException("Data could not be mapped");
        }
    }

    public String deleteUser(Integer userId) {
        try {
            UserEntity userEntity = this.userRepo.findById(userId).orElseThrow(() -> new DataNotFoundException(userId, "User does not exist"));
            this.userRepo.delete(userEntity);
            return "Successfully deleted the user with id : " + userId;
        } catch (DataNotFoundException var3) {
            System.out.println(var3);
            throw new CustomServiceException("User not found for id : " + userId);
        } catch (DataAccessException var4) {
            System.out.println(var4);
            throw new CustomServiceException("Could not fetch data for id : " + userId);
        } catch (ConfigurationException var5) {
            System.out.println(var5);
            throw new CustomServiceException("Data could not be mapped for id : " + userId);
        }
    }
}
