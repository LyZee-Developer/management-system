package com.seng.management_system.util;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    public static String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(auth.getName()).orElse("");
    }

    public static Long getCurrentUserId() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        return user.getId();
    }

    public static String getCurrentUserEmail() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            return "";
        }

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        return user.getEmail();
    }

    
}
