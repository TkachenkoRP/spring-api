package com.example.springapi.web.controller;

import com.example.springapi.mapper.UserMapper;
import com.example.springapi.model.UserEntity;
import com.example.springapi.service.UserService;
import com.example.springapi.web.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Get all users",
            description = "Get all users",
            tags = {"user"}
    )
    @GetMapping
    public ResponseEntity<UserEntityListResponse> findAll() {
        return ResponseEntity.ok(
                userMapper.entityListToListResponse(
                        userService.findAll()
                )
        );
    }

    @Operation(
            summary = "Get user by ID",
            description = "Get user by ID",
            tags = {"user", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserEntityResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserEntityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.entityToResponse(
                userService.findById(id)
        ));
    }

    @Operation(
            summary = "Create user",
            description = "Create user",
            tags = {"user", "create"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = UserEntityResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<UserEntityResponse> create(@RequestBody @Valid UpsertUserEntityRequest request) {
        UserEntity newUser = userService.save(userMapper.requestToEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.entityToResponse(newUser));
    }

    @Operation(
            summary = "Edit user",
            description = "Edit user name",
            tags = {"user", "edit"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserEntityResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserEntityResponse> update(@PathVariable("id") Long userId,
                                                     @RequestBody @Valid UpsertUserEntityRequest user) {
        UserEntity userUpdate = userService.update(userMapper.requestToEntity(userId, user));

        return ResponseEntity.ok(userMapper.entityToResponse(userUpdate));
    }

    @Operation(
            summary = "Delete user by ID",
            description = "Delete user by ID",
            tags = {"user", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
