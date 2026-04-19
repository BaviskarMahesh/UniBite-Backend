package com.unibite.unibit_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final Jwtutil jwtutil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.equals("/auth/login") ||
                path.equals("/auth/register") ||
                path.equals("/auth/refresh") ||
                path.startsWith("/foods") ||
                path.startsWith("/category") ||
                path.startsWith("/menu");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Unauthorized\"}");
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = jwtutil.extractEmail(token);

            System.out.println("🔐 JWT EMAIL: " + email);

            if (email != null && jwtutil.isTokenValid(token, email)) {

                String role = jwtutil.extractRole(token);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println(" AUTH SET FOR: " + email);
            }

        } catch (Exception e) {
            System.out.println("JWT ERROR: " + e.getMessage());

            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Invalid Token\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}