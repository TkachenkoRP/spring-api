package com.example.springapi.rest.web.controller;

import com.example.springapi.exception.EntityNotFoundException;
import com.example.springapi.mapper.UserMapper;
import com.example.springapi.model.UserEntity;
import com.example.springapi.rest.AbstractTestController;
import com.example.springapi.rest.StringTestUtils;
import com.example.springapi.service.UserService;
import com.example.springapi.web.model.UpsertUserEntityRequest;
import com.example.springapi.web.model.UserEntityListResponse;
import com.example.springapi.web.model.UserEntityResponse;
import net.bytebuddy.utility.RandomString;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

        String actualResponse = mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_users_response.json");

        Mockito.verify(userService, Mockito.times(1)).findAll();
        Mockito.verify(userMapper, Mockito.times(1)).entityListToListResponse(users);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenGetUserById_thenReturnUserById() throws Exception {
        UserEntity user = createUser(1L);
        UserEntityResponse userResponse = createUserResponse(1L);

        Mockito.when(userService.findById(1L)).thenReturn(user);
        Mockito.when(userMapper.entityToResponse(user)).thenReturn(userResponse);

        String actualResponse = mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_user_by_id_response.json");

        Mockito.verify(userService, Mockito.times(1)).findById(1L);
        Mockito.verify(userMapper, Mockito.times(1)).entityToResponse(user);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateUser_thenReturnNewUser() throws Exception {
        UserEntity user = new UserEntity();
        user.setName("User1");
        UserEntity createdUser = createUser(1L);
        UserEntityResponse response = createUserResponse(1L);
        UpsertUserEntityRequest request = new UpsertUserEntityRequest("User1");

        Mockito.when(userService.save(user)).thenReturn(createdUser);
        Mockito.when(userMapper.requestToEntity(request)).thenReturn(user);
        Mockito.when(userMapper.entityToResponse(createdUser)).thenReturn(response);

        String actualResponse = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_user_response.json");

        Mockito.verify(userService, Mockito.times(1)).save(user);
        Mockito.verify(userMapper, Mockito.times(1)).requestToEntity(request);
        Mockito.verify(userMapper, Mockito.times(1)).entityToResponse(createdUser);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateUser_thenReturnUpdateUser() throws Exception {
        UpsertUserEntityRequest request = new UpsertUserEntityRequest("New User1");
        UserEntity updatedUser = new UserEntity(1L, "New User1", new ArrayList<>(), new ArrayList<>());
        UserEntityResponse response = new UserEntityResponse(1L, "New User1", 0, 0);

        Mockito.when(userService.update(updatedUser)).thenReturn(updatedUser);
        Mockito.when(userMapper.requestToEntity(1L, request)).thenReturn(updatedUser);
        Mockito.when(userMapper.entityToResponse(updatedUser)).thenReturn(response);

        String actualResponse = mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/update_user_response.json");

        Mockito.verify(userService, Mockito.times(1)).update(updatedUser);
        Mockito.verify(userMapper, Mockito.times(1)).requestToEntity(1L, request);
        Mockito.verify(userMapper, Mockito.times(1)).entityToResponse(updatedUser);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenDeleteUserById_thenReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(userService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenFindByIdNotExistedUser_thenReturnError() throws Exception {
        Mockito.when(userService.findById(500L)).thenThrow(new EntityNotFoundException("Пользователь с ID 500 не найден!"));

        var response = mockMvc.perform(get("/api/user/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/find_user_by_id_not_found_response.json");

        Mockito.verify(userService, Mockito.times(1)).findById(500L);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateUserWithEmptyName_thenReturnError() throws Exception {
        var response = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertUserEntityRequest())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/empty_user_name_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @ParameterizedTest
    @MethodSource("invalidSizeName")
    public void whenCreateUserWithInvalidSizeName_thenReturnError(String name) throws Exception {
        var response = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertUserEntityRequest(name))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/user_name_size_exception_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    private static Stream<Arguments> invalidSizeName() {
        return Stream.of(
                Arguments.of(RandomString.make(2)),
                Arguments.of(RandomString.make(21))
        );
    }


}
