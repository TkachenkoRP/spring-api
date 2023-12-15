package com.example.springapi.rest.web.controller;

import com.example.springapi.mapper.UserMapper;
import com.example.springapi.model.UserEntity;
import com.example.springapi.rest.AbstractTestController;
import com.example.springapi.service.UserService;
import com.example.springapi.web.model.UserEntityListResponse;
import com.example.springapi.web.model.UserEntityResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest extends AbstractTestController {

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void whenFindAll_thenReturnAllUsers() throws Exception {
        List<UserEntity> users = new ArrayList<>();
        users.add(createUser(1L));
        users.add(createUser(2L));

        List<UserEntityResponse> userEntityResponses = new ArrayList<>();
        userEntityResponses.add(createUserResponse(1L));
        userEntityResponses.add(createUserResponse(2L));

        UserEntityListResponse userEntityListResponse = new UserEntityListResponse(userEntityResponses);

        Mockito.when(userService.findAll()).thenReturn(users);

        Mockito.when(userMapper.entityListToListResponse(users)).thenReturn(userEntityListResponse);


    }


}
