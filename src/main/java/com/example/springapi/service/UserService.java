package com.example.springapi.service;

import com.example.springapi.model.RoleEntity;
import com.example.springapi.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();

    UserEntity findById(Long id);

    UserEntity save(UserEntity user, RoleEntity role);

    UserEntity update(UserEntity user);

    void deleteById(Long id);

    UserEntity findByName(String name);
}
