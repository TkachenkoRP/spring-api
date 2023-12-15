package com.example.springapi.mapper;

import com.example.springapi.model.CommentEntity;
import com.example.springapi.web.model.CommentEntityListResponse;
import com.example.springapi.web.model.CommentEntityResponse;
import com.example.springapi.web.model.UpsertCommentEntityRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;
@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface CommentMapper {
    CommentEntity requestToEntity(UpsertCommentEntityRequest request);

    @Mapping(source = "commentId", target = "id")
    CommentEntity requestToEntity(Long commentId, UpsertCommentEntityRequest request);

    CommentEntityResponse entityToResponse(CommentEntity comment);

    default CommentEntityListResponse entityListToListResponse(List<CommentEntity> comments) {
        CommentEntityListResponse response = new CommentEntityListResponse();
        response.setComments(comments.stream()
                .map(this::entityToResponse).collect(Collectors.toList()));
        return response;
    }
}
