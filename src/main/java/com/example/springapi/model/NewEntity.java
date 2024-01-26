package com.example.springapi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@FieldNameConstants
@Entity(name = "news")
public class NewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;
    private String content;
    @CreationTimestamp
    private Instant createAt;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private CategoryEntity category;
    @OneToMany(mappedBy = "newEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();
}
