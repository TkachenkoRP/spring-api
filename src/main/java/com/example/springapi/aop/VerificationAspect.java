package com.example.springapi.aop;

import com.example.springapi.exception.VerificationException;
import com.example.springapi.model.CommentEntity;
import com.example.springapi.model.NewEntity;
import com.example.springapi.model.UserEntity;
import com.example.springapi.security.AppUserPrincipal;
import com.example.springapi.service.impl.DatabaseCommentService;
import com.example.springapi.service.impl.DatabaseNewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class VerificationAspect {

    private final DatabaseNewService databaseNewService;
    private final DatabaseCommentService databaseCommentService;

    @Before("@annotation(CheckVerificationRoleUser)")
    public void checkUserRole(JoinPoint joinPoint) {
        performVerification(joinPoint, false);
    }

    @Before("@annotation(CheckVerificationRoleAll)")
    public void checkAllRole(JoinPoint joinPoint) {
        performVerification(joinPoint, true);
    }

    private void performVerification(JoinPoint joinPoint, boolean checkAllRoles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return;
        }

        AppUserPrincipal userDetails = (AppUserPrincipal) authentication.getPrincipal();

        Long userId = userDetails.getUserID();

        Object[] args = joinPoint.getArgs();
        Long userIdFromRequest = 0L;
        if (args.length > 0) {
            Object firstArg = args[0];
            if (firstArg instanceof UserEntity) {
                userIdFromRequest = ((UserEntity) firstArg).getId();
            } else if (firstArg instanceof CommentEntity) {
                userIdFromRequest = databaseCommentService.findById(((CommentEntity) firstArg).getId()).getUser().getId();
            } else if (firstArg instanceof NewEntity) {
                userIdFromRequest = databaseNewService.findById(((NewEntity) firstArg).getId()).getUser().getId();
            } else if (firstArg instanceof Long) {
                userIdFromRequest = getUserIdFromService(joinPoint.getSignature().getDeclaringType().getSimpleName(), (Long) firstArg);
            }
        }

        if (!userId.equals(userIdFromRequest) && !checkAllRoles) {
            throw new VerificationException("Вы не можете работать с чужими записями!");
        }
    }

    private Long getUserIdFromService(String serviceName, Long arg) {
        if (serviceName.equals(DatabaseNewService.class.getSimpleName())) {
            return databaseNewService.findById(arg).getUser().getId();
        } else if (serviceName.equals(DatabaseCommentService.class.getSimpleName())) {
            return databaseCommentService.findById(arg).getUser().getId();
        }
        return arg;
    }
}
