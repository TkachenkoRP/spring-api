package com.example.springapi.repository;

import com.example.springapi.model.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatabaseUserRepository extends JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findByName(String name);
}
