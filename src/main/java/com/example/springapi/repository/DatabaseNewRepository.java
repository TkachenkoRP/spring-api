package com.example.springapi.repository;

import com.example.springapi.model.NewEntity;
import com.example.springapi.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DatabaseNewRepository extends JpaRepository<NewEntity, Long>, JpaSpecificationExecutor<NewEntity> {
    List<NewEntity> findAllByUserId(Long userId);
    List<NewEntity> findAllByCategoryId(Long categoryId);
}
