package com.seng.management_system.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class AuthenticationUtil {
    public static String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(auth.getName()).orElse("");
    }
}
