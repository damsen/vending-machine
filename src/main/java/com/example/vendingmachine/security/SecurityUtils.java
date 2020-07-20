package com.example.vendingmachine.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static final String ADMIN = "ROLE_ADMIN";

    public static boolean isLoggedIn() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        return auth != null &&
               auth.isAuthenticated();
    }

    public static boolean isLoggedInAsAdmin() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        return auth != null &&
               auth.isAuthenticated() &&
               auth.getAuthorities().stream().map(String::valueOf).anyMatch(ADMIN::equalsIgnoreCase);
    }

}
