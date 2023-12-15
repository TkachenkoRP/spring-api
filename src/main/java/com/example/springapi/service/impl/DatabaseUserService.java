package com.example.springapi.service.impl;

import com.example.springapi.exception.EntityNotFoundException;
import com.example.springapi.model.UserEntity;
import com.example.springapi.repository.DatabaseUserRepository;
import com.example.springapi.service.UserService;
import com.example.springapi.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {
    private final DatabaseUserRepository repository;

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с ID {0} не найден!", id
                )));
    }

    @Override
    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }

    @Override
    public UserEntity update(UserEntity user) {
        UserEntity existedUser = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existedUser);
        return repository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
