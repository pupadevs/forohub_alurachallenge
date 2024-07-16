package com.forohub.api.config.security;

import com.forohub.api.app.service.TokenService;
import com.forohub.api.infrastructure.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class SecurityFilter extends OncePerRequestFilter {
@Autowired
private TokenService tokenService;
@Autowired
private UserRepository usuarioRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
    try{
        if (requestURI.equals("/register") || requestURI.equals("/login")) {
            filterChain.doFilter(request, response); // Pasar al siguiente filtro
            return;
        }
        if(authHeader == null){
            throw new TokenEmptyException("Token is empty");
        }

        var token = authHeader.replace("Bearer ", "");
        var email = tokenService.getSubject(token);// extract username
        if (email == null) {
            throw new TokenEmptyException("Token invalid");

        }
        var user = usuarioRepository.findByEmail(email);
        var authentication = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities()); // Forzamos un inicio de sesion
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }catch (TokenEmptyException ex){
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());

    }




    }
}
