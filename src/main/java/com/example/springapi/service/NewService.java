package com.example.springapi.service;

import com.example.springapi.aop.CheckVerification;
import com.example.springapi.model.NewEntity;
import com.example.springapi.web.model.NewEntityFilter;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface NewService {
    List<NewEntity> filterBy(NewEntityFilter filter);

    List<NewEntity> findAll();

    NewEntity findById(Long id);

    NewEntity save(NewEntity newEntity);

    NewEntity update(NewEntity newEntity);

    void deleteById(Long id);

    List<NewEntity> findAllByUserId(Long userId);

    List<NewEntity> findAllByCategoryId(Long categoryId);
}
