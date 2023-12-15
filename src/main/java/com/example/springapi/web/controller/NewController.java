package com.example.springapi.web.controller;

import com.example.springapi.mapper.NewMapper;
import com.example.springapi.model.NewEntity;
import com.example.springapi.service.NewService;
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
@RequestMapping("api/new")
@RequiredArgsConstructor
@Tag(name = "New", description = "New API")
public class NewController {
    private final NewService newService;
    private final NewMapper newMapper;

    @Operation(
            summary = "Get all news with filter",
            description = "Get all news with filter",
            tags = {"new", "filter"}
    )
    @GetMapping("/filter")
    public ResponseEntity<NewEntityListResponse> filterBy(NewEntityFilter filter) {
        return ResponseEntity.ok(
                newMapper.entityListToListResponse(newService.filterBy(
                        filter
                ))
        );
    }

    @Operation(
            summary = "Get all news",
            description = "Get all news",
            tags = {"new"}
    )
    @GetMapping
    public ResponseEntity<NewEntityListResponse> findAll() {
        return ResponseEntity.ok(newMapper.entityListToListResponse(
                newService.findAll()
        ));
    }

    @Operation(
            summary = "Get new by ID",
            description = "Get new by ID",
            tags = {"new", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewEntityResponse.class), mediaType = "application/json")
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
    public ResponseEntity<NewEntityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newMapper.entityToResponse(newService.findById(id)));
    }

    @Operation(
            summary = "Create new",
            description = "Create new",
            tags = {"new", "create"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = NewEntityResponse.class), mediaType = "application/json")
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
    public ResponseEntity<NewEntityResponse> create(@RequestBody @Valid UpsertNewEntityRequest newEntity) {
        NewEntity newEntityCreate = newService.save(newMapper.requestToEntity(newEntity));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newMapper.entityToResponse(newEntityCreate));
    }

    @Operation(
            summary = "Edit new",
            description = "Edit new",
            tags = {"new", "edit"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewEntityResponse.class), mediaType = "application/json")
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
    public ResponseEntity<NewEntityResponse> update(@PathVariable("id") Long newId,
                                                    @RequestBody @Valid UpsertNewEntityRequest newEntity) {
        NewEntity newEntityUpdate = newService.update(
                newMapper.requestToEntity(newId, newEntity)
        );

        return ResponseEntity.ok(newMapper.entityToResponse(newEntityUpdate));
    }

    @Operation(
            summary = "Delete new by ID",
            description = "Delete new by ID",
            tags = {"new", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
