package com.example.springapi.service.impl;

import com.example.springapi.aop.CheckVerificationRoleAll;
import com.example.springapi.aop.CheckVerificationRoleUser;
import com.example.springapi.exception.EntityNotFoundException;
import com.example.springapi.model.CommentEntity;
import com.example.springapi.model.UserEntity;
import com.example.springapi.repository.DatabaseCommentRepository;
import com.example.springapi.service.CommentService;
import com.example.springapi.service.UserService;
import com.example.springapi.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCommentService implements CommentService {

    private final DatabaseCommentRepository repository;
    private final UserService userService;

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
    public CommentEntity save(CommentEntity comment, Long userId) {
        UserEntity user = userService.findById(userId);
        comment.setUser(user);
        return repository.save(comment);
    }

    @Override
    @CheckVerificationRoleAll
    public CommentEntity update(CommentEntity comment) {
        CommentEntity existedComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existedComment);
        return repository.save(existedComment);
    }

    @Override
    @CheckVerificationRoleUser
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
