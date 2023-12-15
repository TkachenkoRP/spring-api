package com.example.springapi.mapper;

import com.example.springapi.model.NewEntity;
import com.example.springapi.web.model.NewEntityForListResponse;
import com.example.springapi.web.model.NewEntityListResponse;
import com.example.springapi.web.model.NewEntityResponse;
import com.example.springapi.web.model.UpsertNewEntityRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(NewMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, CategoryMapper.class, CommentMapper.class})
public interface NewMapper {
    NewEntity requestToEntity(UpsertNewEntityRequest request);

    @Mapping(source = "newId", target = "id")
    NewEntity requestToEntity(Long newId, UpsertNewEntityRequest request);

    NewEntityResponse entityToResponse(NewEntity newEntity);

    NewEntityForListResponse entityToResponseList(NewEntity newEntity);

    default NewEntityListResponse entityListToListResponse(List<NewEntity> news) {
        NewEntityListResponse response = new NewEntityListResponse();
        response.setNews(news.stream()
                .map(this::entityToResponseList).collect(Collectors.toList()));
        return response;
    }
}
