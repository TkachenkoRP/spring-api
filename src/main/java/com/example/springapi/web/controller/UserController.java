package com.example.springapi.web.controller;

import com.example.springapi.mapper.UserMapper;
import com.example.springapi.model.RoleEntity;
import com.example.springapi.model.RoleType;
import com.example.springapi.model.UserEntity;
import com.example.springapi.service.UserService;
import com.example.springapi.web.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
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
    public ResponseEntity<UserEntityResponse> create(@RequestBody @Validated(UpsertUserEntityRequest.CreateValidationGroup.class) UpsertUserEntityRequest request,
                                                     @RequestParam RoleType roleType) {
        UserEntity newUser = userService.save(userMapper.requestToEntity(request), RoleEntity.from(roleType));
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<UserEntityResponse> update(@PathVariable("id") Long userId,
                                                     @RequestBody @Validated(UpsertUserEntityRequest.UpdateValidationGroup.class) UpsertUserEntityRequest user) {
        UserEntity userUpdate = userService.update(userMapper.requestToEntity(userId, user));

        return ResponseEntity.ok(userMapper.entityToResponse(userUpdate));
    }

    @Operation(
            summary = "Delete user by ID",
            description = "Delete user by ID",
            tags = {"user", "id"}
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
