package com.example.springapi.service.impl;

import com.example.springapi.aop.CheckVerification;
import com.example.springapi.exception.EntityNotFoundException;
import com.example.springapi.model.CommentEntity;
import com.example.springapi.repository.DatabaseCommentRepository;
import com.example.springapi.service.CommentService;
import com.example.springapi.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCommentService implements CommentService {

    private final DatabaseCommentRepository repository;

    @Override
    public List<CommentEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public CommentEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Коментарий с ID {0} не найден!", id
                )));
    }

    @Override
    public CommentEntity save(CommentEntity comment) {
        return repository.save(comment);
    }

    @Override
    @CheckVerification
    public CommentEntity update(CommentEntity comment) {
        CommentEntity existedComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existedComment);
        return repository.save(existedComment);
    }

    @Override
    @CheckVerification
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CommentEntity> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public List<CommentEntity> findAllByNewEntityId(Long newEntityId) {
        return repository.findAllByNewEntityId(newEntityId);
    }
}
