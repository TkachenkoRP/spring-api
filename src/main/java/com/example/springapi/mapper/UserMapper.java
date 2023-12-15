package com.example.springapi.mapper;

import com.example.springapi.model.UserEntity;
import com.example.springapi.web.model.UpsertUserEntityRequest;
import com.example.springapi.web.model.UserEntityListResponse;
import com.example.springapi.web.model.UserEntityResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserEntity requestToEntity(UpsertUserEntityRequest request);

    @Mapping(source = "userId", target = "id")
    UserEntity requestToEntity(Long userId, UpsertUserEntityRequest request);

    UserEntityResponse entityToResponse(UserEntity user);

    default UserEntityListResponse entityListToListResponse(List<UserEntity> users) {
        UserEntityListResponse response = new UserEntityListResponse();
        response.setUsers(users.stream()
                .map(this::entityToResponse).collect(Collectors.toList()));
        return response;
    }
}
