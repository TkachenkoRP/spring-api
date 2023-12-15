package com.example.springapi.mapper;

import com.example.springapi.model.CategoryEntity;
import com.example.springapi.service.NewService;
import com.example.springapi.web.model.CategoryEntityResponse;
import com.example.springapi.web.model.UpsertCategoryEntityRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CategoryMapperDelegate implements CategoryMapper {

    @Autowired
    private NewService newService;

    @Override
    public CategoryEntity requestToEntity(UpsertCategoryEntityRequest request) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(request.getName());
        return entity;
    }

    @Override
    public CategoryEntity requestToEntity(Long categoryId, UpsertCategoryEntityRequest request) {
        CategoryEntity entity = requestToEntity(request);
        entity.setId(categoryId);
        entity.setNews(newService.findAllByCategoryId(categoryId));
        return entity;
    }

    @Override
    public CategoryEntityResponse entityToResponse(CategoryEntity entity) {
        CategoryEntityResponse response = new CategoryEntityResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        //TODO Заменить на запрос?
        response.setCountNews(entity.getNews().size());
        return response;
    }
}
