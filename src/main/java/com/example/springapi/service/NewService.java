package com.example.springapi.service;

import com.example.springapi.model.NewEntity;
import com.example.springapi.web.model.NewEntityFilter;

import java.util.List;

public interface NewService {
    List<NewEntity> filterBy(NewEntityFilter filter);

    List<NewEntity> findAll();

    NewEntity findById(Long id);

    NewEntity save(NewEntity newEntity, Long userId);

    NewEntity update(NewEntity newEntity);

    void deleteById(Long id);

    List<NewEntity> findAllByUserId(Long userId);

    List<NewEntity> findAllByCategoryId(Long categoryId);
}
