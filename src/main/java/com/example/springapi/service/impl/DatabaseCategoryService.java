package com.example.springapi.service.impl;

import com.example.springapi.exception.EntityNotFoundException;
import com.example.springapi.model.CategoryEntity;
import com.example.springapi.repository.DatabaseCategoryRepository;
import com.example.springapi.service.CategoryService;
import com.example.springapi.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCategoryService implements CategoryService {
    private final DatabaseCategoryRepository repository;

    @Override
    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public CategoryEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Категория с ID {0} не найдена!", id
                )));
    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return repository.save(category);
    }

    @Override
    public CategoryEntity update(CategoryEntity category) {
        CategoryEntity existedCategory = findById(category.getId());
        BeanUtils.copyNonNullProperties(category, existedCategory);
        return repository.save(existedCategory);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
