package com.example.springapi.service;

import com.example.springapi.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();

    UserEntity findById(Long id);

    UserEntity save(UserEntity user);

    UserEntity update(UserEntity user);

    void deleteById(Long id);
}
