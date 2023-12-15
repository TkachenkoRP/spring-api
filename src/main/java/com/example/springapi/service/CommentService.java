package com.example.springapi.service;

import com.example.springapi.model.CommentEntity;

import java.util.List;

public interface CommentService {
    List<CommentEntity> findAll();

    CommentEntity findById(Long id);

    CommentEntity save(CommentEntity user);

    CommentEntity update(CommentEntity user);

    void deleteById(Long id);

    List<CommentEntity> findAllByUserId(Long userId);

    List<CommentEntity> findAllByNewEntityId(Long newEntityId);
}
