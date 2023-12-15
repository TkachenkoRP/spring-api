package com.example.springapi.repository;

import com.example.springapi.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatabaseCommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByUserId(Long userId);
    List<CommentEntity> findAllByNewEntityId(Long newEntityId);
}
