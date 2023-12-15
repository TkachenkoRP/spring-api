package com.example.springapi.repository;

import com.example.springapi.model.NewEntity;
import com.example.springapi.web.model.NewEntityFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewEntitySpecification {
    static Specification<NewEntity> withFilter(NewEntityFilter filter) {
        return Specification.where(byUserId(filter.getUserId()))
                .and(byCategoryId(filter.getCategoryId()));
    }

    static Specification<NewEntity> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(NewEntity.Fields.user).get("id"), userId);
        };
    }

    static Specification<NewEntity> byCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(NewEntity.Fields.category).get("id"), categoryId);
        };
    }

}
