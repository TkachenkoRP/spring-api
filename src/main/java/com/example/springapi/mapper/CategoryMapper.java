package com.example.springapi.mapper;

import com.example.springapi.model.CategoryEntity;
import com.example.springapi.web.model.CategoryEntityListResponse;
import com.example.springapi.web.model.CategoryEntityResponse;
import com.example.springapi.web.model.UpsertCategoryEntityRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(CategoryMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryEntity requestToEntity(UpsertCategoryEntityRequest request);

    @Mapping(source = "categoryId", target = "id")
    CategoryEntity requestToEntity(Long categoryId, UpsertCategoryEntityRequest request);

    CategoryEntityResponse entityToResponse(CategoryEntity category);

    default CategoryEntityListResponse entityListToListResponse(List<CategoryEntity> categories) {
        CategoryEntityListResponse response = new CategoryEntityListResponse();
        response.setCategories(categories.stream()
                .map(this::entityToResponse).collect(Collectors.toList()));
        return response;
    }

}
