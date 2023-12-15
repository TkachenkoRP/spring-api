package com.example.springapi.mapper;

import com.example.springapi.model.UserEntity;
import com.example.springapi.service.CommentService;
import com.example.springapi.service.NewService;
import com.example.springapi.web.model.UpsertUserEntityRequest;
import com.example.springapi.web.model.UserEntityResponse;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UserMapperDelegate implements UserMapper {

    @Autowired
    private NewService newService;
    @Autowired
    private CommentService commentService;

    @Override
    public UserEntity requestToEntity(UpsertUserEntityRequest request) {
        UserEntity entity = new UserEntity();
        entity.setName(request.getName());
        return entity;
    }

    @Override
    public UserEntity requestToEntity(Long userId, UpsertUserEntityRequest request) {
        UserEntity entity = requestToEntity(request);
        entity.setId(userId);
        entity.setNews(newService.findAllByUserId(userId));
        entity.setComments(commentService.findAllByUserId(userId));
        return entity;
    }

    @Override
    public UserEntityResponse entityToResponse(UserEntity entity) {
        UserEntityResponse response = new UserEntityResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        //TODO Заменить на запрос?
        response.setCountNew(entity.getNews().size());
        response.setCountComment(entity.getComments().size());
        return response;
    }
}
