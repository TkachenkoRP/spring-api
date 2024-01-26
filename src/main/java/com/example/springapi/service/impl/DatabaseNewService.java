package com.example.springapi.service.impl;

import com.example.springapi.aop.CheckVerificationRoleAll;
import com.example.springapi.aop.CheckVerificationRoleUser;
import com.example.springapi.exception.EntityNotFoundException;
import com.example.springapi.model.NewEntity;
import com.example.springapi.model.UserEntity;
import com.example.springapi.repository.DatabaseNewRepository;
import com.example.springapi.repository.NewEntitySpecification;
import com.example.springapi.service.NewService;
import com.example.springapi.service.UserService;
import com.example.springapi.utils.BeanUtils;
import com.example.springapi.web.model.NewEntityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseNewService implements NewService {
    private final DatabaseNewRepository repository;
    private final UserService userService;

    @Override
    public List<NewEntity> filterBy(NewEntityFilter filter) {
        return repository.findAll(NewEntitySpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(), filter.getPageSize()
                )).getContent();
    }

    @Override
    public List<NewEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public NewEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Новость с ID {0} не найдена!", id
                )));
    }

    @Override
    public NewEntity save(NewEntity newEntity, Long userId) {
        UserEntity user = userService.findById(userId);
        newEntity.setUser(user);
        return repository.save(newEntity);
    }

    @Override
    @CheckVerificationRoleAll
    public NewEntity update(NewEntity newEntity) {
        NewEntity existedNewEntity = findById(newEntity.getId());
        BeanUtils.copyNonNullProperties(newEntity, existedNewEntity);
        return repository.save(existedNewEntity);
    }

    @Override
    @CheckVerificationRoleUser
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<NewEntity> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public List<NewEntity> findAllByCategoryId(Long categoryId) {
        return repository.findAllByCategoryId(categoryId);
    }

}
