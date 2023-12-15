package com.example.springapi.aop;

import com.example.springapi.exception.VerificationException;
import com.example.springapi.model.CommentEntity;
import com.example.springapi.model.NewEntity;
import com.example.springapi.service.NewService;
import com.example.springapi.service.impl.DatabaseCommentService;
import com.example.springapi.service.impl.DatabaseNewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class VerificationAspect {

    private final DatabaseNewService databaseNewService;
    private final DatabaseCommentService databaseCommentService;

    @Before("@annotation(CheckVerification)")
    public void check(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long userId = 0L;
        if (args.length > 0) {
            Object firstArg = args[0];
            if (firstArg instanceof CommentEntity) {
                userId = ((CommentEntity) firstArg).getUser().getId();
            } else if (firstArg instanceof NewEntity) {
                userId = ((NewEntity) firstArg).getUser().getId();
            } else if (firstArg instanceof Long) {
                userId = getUserIdFromService(joinPoint.getSignature().getDeclaringType().getSimpleName(), (Long) firstArg);
            }
        }

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String header = request.getHeader("user");
        Long id = header != null ? Long.parseLong(header) : -1L;

        if (!id.equals(userId)) {
            throw new VerificationException("Вы не можете работать с чужими записями!");
        }
    }

    private Long getUserIdFromService(String serviceName, Long arg) {
        if (serviceName.equals(DatabaseNewService.class.getSimpleName())) {
            return databaseNewService.findById(arg).getUser().getId();
        } else if (serviceName.equals(DatabaseCommentService.class.getSimpleName())) {
            return databaseCommentService.findById(arg).getUser().getId();
        }
        return 0L;
    }
}
