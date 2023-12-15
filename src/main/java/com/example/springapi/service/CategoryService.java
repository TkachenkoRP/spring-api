package com.example.springapi.service;

import com.example.springapi.model.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> findAll();

    CategoryEntity findById(Long id);

    CategoryEntity save(CategoryEntity user);

    CategoryEntity update(CategoryEntity user);

    void deleteById(Long id);
}
