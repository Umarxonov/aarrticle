package com.example.article.secret;

import com.example.article.entity.User;
import com.example.article.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager manager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        User user = getUserFromRequest(httpServletRequest);
        if (user != null
                && user.isAccountNonExpired()
                && user.isAccountNonLocked()
                && user.isCredentialsNonExpired()
                && user.isEnabled()) {
            UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public User getUserFromRequest(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");

        if (auth != null) {
            auth = auth.substring(7);
            boolean validateToken = jwtProvider.validateJwtToken(auth);
            if (validateToken) {
                UUID uuid = UUID.fromString(jwtProvider.getUserIdFromToken(auth));
                User user = userRepository.getById(uuid);
                return user;
            }

        }

        return null;
    }
//    public User getUserFromRequest(HttpServletRequest request) {
//        String authorization = request.getHeader("Authorization");
//        if (authorization != null) {
//            String token = authorization.substring(7);
//            boolean validateJwtToken = jwtProvider.validateJwtToken(token);
//            if (validateJwtToken) {
//                String userIdFromToken = jwtProvider.getUserIdFromToken(token);
//                return userRepository.getById(UUID.fromString(userIdFromToken));
//            }
//        }
//        return null;
//    }
}
