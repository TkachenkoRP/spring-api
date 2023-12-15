package com.example.springapi.web.controller;

import com.example.springapi.mapper.CommentMapper;
import com.example.springapi.model.CommentEntity;
import com.example.springapi.service.CommentService;
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

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "Comment API")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Operation(
            summary = "Get all comments",
            description = "Get all comments",
            tags = {"comment"}
    )
    @GetMapping
    public ResponseEntity<CommentEntityListResponse> findAll() {
        return ResponseEntity.ok(commentMapper.entityListToListResponse(
                commentService.findAll()
        ));
    }

    @Operation(
            summary = "Get comment by ID",
            description = "Get comment by ID.",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CommentEntityResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentEntityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commentMapper.entityToResponse(commentService.findById(id)));
    }

    @Operation(
            summary = "Create comment",
            description = "Create comment",
            tags = {"comment", "create"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = CommentEntityResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentEntityResponse> create(@RequestBody @Valid UpsertCommentEntityRequest request) {
        CommentEntity commentEntity = commentService.save(commentMapper.requestToEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.entityToResponse(commentEntity));
    }

    @Operation(
            summary = "Edit comment",
            description = "Edit comment",
            tags = {"comment", "edit"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CommentEntityResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentEntityResponse> update(@PathVariable("id") Long commentId,
                                                        @RequestBody @Valid UpsertCommentEntityRequest request) {
        CommentEntity commentEntity = commentService.update(
                commentMapper.requestToEntity(commentId, request)
        );
        return ResponseEntity.ok(commentMapper.entityToResponse(commentEntity));
    }

    @Operation(
            summary = "Delete comment by ID",
            description = "Delete comment by ID",
            tags = {"category", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
