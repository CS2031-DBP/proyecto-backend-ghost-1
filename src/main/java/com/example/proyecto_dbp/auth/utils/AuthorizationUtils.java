package com.example.proyecto_dbp.auth.utils;

import com.example.proyecto_dbp.config.JwtAuthenticationFilter;
import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserRepository userRepository;

    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return jwtAuthenticationFilter;
    }

    public String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) return ((UserDetails) principal).getUsername();
        else return null;
    }

    public boolean isAdminOrResourceOwner(User user) {
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null) return false;
        User currentUser = userRepository.findByEmail(currentUserEmail).orElse(null);
        return currentUser != null && (currentUser.getRoles().contains("ROLE_ADMIN") || currentUser.getId().equals(user.getId()));
    }
}
