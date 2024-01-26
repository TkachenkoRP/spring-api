package com.example.springapi.web.controller;

import com.example.springapi.mapper.CategoryMapper;
import com.example.springapi.model.CategoryEntity;
import com.example.springapi.service.CategoryService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category API")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(
            summary = "Get categories",
            description = "Get categories",
            tags = {"category"}
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<CategoryEntityListResponse> findAll() {
        return ResponseEntity.ok(categoryMapper.entityListToListResponse(
                categoryService.findAll()
        ));
    }

    @Operation(
            summary = "Get category by ID",
            description = "Get category by ID. Return id, name, count news in category",
            tags = {"category", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryEntityResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CategoryEntityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.entityToResponse(categoryService.findById(id)));
    }

    @Operation(
            summary = "Create category",
            description = "Create category",
            tags = {"category", "create"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryEntityResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping
    public ResponseEntity<CategoryEntityResponse> create(@RequestBody @Valid UpsertCategoryEntityRequest categoryEntity) {
        CategoryEntity categoryEntityCreate = categoryService.save(categoryMapper.requestToEntity(categoryEntity));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.entityToResponse(categoryEntityCreate));
    }

    @Operation(
            summary = "Edit category",
            description = "Edit category name",
            tags = {"category", "edit"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryEntityResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryEntityResponse> update(@PathVariable("id") Long categoryId,
                                                         @RequestBody @Valid UpsertCategoryEntityRequest categoryEntity) {
        CategoryEntity categoryEntityUpdate = categoryService.update(
                categoryMapper.requestToEntity(categoryId, categoryEntity)
        );
        return ResponseEntity.ok(categoryMapper.entityToResponse(categoryEntityUpdate));
    }

    @Operation(
            summary = "Delete category by ID",
            description = "Delete category by ID",
            tags = {"category", "id"}
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
