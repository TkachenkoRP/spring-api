package com.example.springapi.mapper;

import com.example.springapi.model.CommentEntity;
import com.example.springapi.service.NewService;
import com.example.springapi.service.UserService;
import com.example.springapi.web.model.CommentEntityResponse;
import com.example.springapi.web.model.UpsertCommentEntityRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NewService newService;
    @Autowired
    private UserService userService;


    @Override
    public CommentEntity requestToEntity(UpsertCommentEntityRequest request) {
        CommentEntity entity = new CommentEntity();
        entity.setText(request.getText());
        entity.setNewEntity(newService.findById(request.getNewId()));
        entity.setUser(userService.findById(request.getUserId()));
        return entity;
    }

    @Override
    public CommentEntity requestToEntity(Long commentId, UpsertCommentEntityRequest request) {
        CommentEntity entity = requestToEntity(request);
        entity.setId(commentId);
        return entity;
    }

    @Override
    public CommentEntityResponse entityToResponse(CommentEntity entity) {
        CommentEntityResponse response = new CommentEntityResponse();
        response.setId(entity.getId());
        response.setText(entity.getText());
        response.setCreateAt(entity.getCreateAt());
        response.setUser(userMapper.entityToResponse(entity.getUser()));
        response.setFromNewId(entity.getNewEntity().getId());
        return response;
    }
}
