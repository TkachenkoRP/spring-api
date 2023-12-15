package com.example.springapi.repository;

import com.example.springapi.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseCategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
