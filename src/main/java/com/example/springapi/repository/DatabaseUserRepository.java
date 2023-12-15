package com.example.springapi.repository;

import com.example.springapi.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseUserRepository extends JpaRepository<UserEntity, Long> {
}
