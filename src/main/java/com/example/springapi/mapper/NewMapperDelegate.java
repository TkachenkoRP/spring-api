package com.example.springapi.mapper;

import com.example.springapi.model.CommentEntity;
import com.example.springapi.model.NewEntity;
import com.example.springapi.service.CategoryService;
import com.example.springapi.service.CommentService;
import com.example.springapi.service.UserService;
import com.example.springapi.web.model.NewEntityForListResponse;
import com.example.springapi.web.model.UpsertNewEntityRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class NewMapperDelegate implements NewMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;

    @Override
    public NewEntity requestToEntity(UpsertNewEntityRequest request) {
        NewEntity entity = new NewEntity();
        entity.setContent(request.getContent());
        entity.setUser(userService.findById(request.getUserId()));
        entity.setCategory(categoryService.findById(request.getCategoryId()));
        return entity;
    }

    @Override
    public NewEntity requestToEntity(Long newId, UpsertNewEntityRequest request) {
        NewEntity entity = requestToEntity(request);
        entity.setId(newId);
        entity.setComments(commentService.findAllByNewEntityId(newId));
        return entity;
    }

    @Override
    public NewEntityForListResponse entityToResponseList(NewEntity entity) {
        NewEntityForListResponse response = new NewEntityForListResponse();
        response.setId(entity.getId());
        response.setCreateAt(entity.getCreateAt());
        response.setContent(entity.getContent());
        response.setUser(userMapper.entityToResponse(entity.getUser()));
        response.setCategory(categoryMapper.entityToResponse(entity.getCategory()));
        response.setCountComments(entity.getComments().size());
        return response;
    }
}
