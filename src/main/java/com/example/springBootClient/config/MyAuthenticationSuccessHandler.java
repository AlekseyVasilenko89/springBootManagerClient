package com.example.springBootClient.config;

import com.example.springBootClient.models.User;
import com.example.springBootClient.models.UserRoles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws
            IOException {
        User principal = (User) authentication.getPrincipal();
        boolean isAdmin = false;
        boolean isUser = false;
        boolean isAnonymous = false;
        for (UserRoles userRole : principal.getAuthorities()) {
            if (userRole.getAuthority().equalsIgnoreCase("admin")) {
                isAdmin = true;
            }
            if (userRole.getAuthority().equalsIgnoreCase("user")) {
                isUser = true;
            }
        }

        if (isUser) {
            response.sendRedirect("/user/forUsers/" + principal.getId());
        } else if (isAdmin) {
            response.sendRedirect("/admin/showUsers");
        } else if (isAnonymous) {
            response.sendRedirect("/login");
        } else {
            throw new IllegalStateException();
        }
    }
}
