package com.example.springapi.service.impl;

import com.example.springapi.aop.CheckVerificationRoleUser;
import com.example.springapi.exception.EntityNotFoundException;
import com.example.springapi.exception.UserRegistrationException;
import com.example.springapi.model.RoleEntity;
import com.example.springapi.model.UserEntity;
import com.example.springapi.repository.DatabaseUserRepository;
import com.example.springapi.service.UserService;
import com.example.springapi.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {
    private final DatabaseUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    @CheckVerificationRoleUser
    public UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с ID {0} не найден!", id
                )));
    }

    @Override
    public UserEntity save(UserEntity user, RoleEntity role) {
        if (repository.findByName(user.getName()).isPresent()) {
            throw new UserRegistrationException(MessageFormat.format(
                    "Пользователь с именем {0} уже зарегистрирован!", user.getName())
            );
        }
        role.setUser(user);
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.saveAndFlush(user);
    }

    @Override
    @CheckVerificationRoleUser
    public UserEntity update(UserEntity user) {
        UserEntity existedUser = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existedUser);
        return repository.save(existedUser);
    }

    @Override
    @CheckVerificationRoleUser
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserEntity findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с именем {0} не найден!", name
                )));
    }
}
