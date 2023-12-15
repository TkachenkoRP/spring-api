package com.example.springapi.rest;

import com.example.springapi.model.CategoryEntity;
import com.example.springapi.model.CommentEntity;
import com.example.springapi.model.NewEntity;
import com.example.springapi.model.UserEntity;
import com.example.springapi.web.model.CategoryEntityResponse;
import com.example.springapi.web.model.UserEntityResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected CategoryEntity createCategory(Long id) {
        CategoryEntity category = new CategoryEntity(
                id,
                "Category" + id,
                new ArrayList<>()
        );
        return category;
    }

    protected UserEntity createUser(Long id) {
        UserEntity user = new UserEntity(
                id,
                "User" + id,
                new ArrayList<>(),
                new ArrayList<>()
        );
        return user;
    }

    protected NewEntity createNew(Long id, UserEntity user, CategoryEntity category) {
        NewEntity newEntity = new NewEntity(
                id,
                user,
                "Content" + id,
                Instant.now(),
                category,
                new ArrayList<>()
        );
        return newEntity;
    }

    protected CommentEntity createComment(Long id, NewEntity newEntity, UserEntity user) {
        CommentEntity comment = new CommentEntity(
                id,
                "Text" + id,
                Instant.now(),
                newEntity,
                user
        );
        return comment;
    }

    protected CategoryEntityResponse createCategoryResponse(Long id) {
        CategoryEntityResponse response = new CategoryEntityResponse(
                id,
                "Category" + id,
                0
        );
        return response;
    }

    protected UserEntityResponse createUserResponse(Long id) {
        UserEntityResponse response = new UserEntityResponse(
                id,
                "User" + 1,
                0,
                0
                );
        return response;
    }


}
