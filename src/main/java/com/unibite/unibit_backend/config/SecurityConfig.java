package com.unibite.unibit_backend.config;

import com.unibite.unibit_backend.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity   // ✅ IMPORTANT
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        /// PUBLIC
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/menu/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/foods/**").permitAll()   // ✅ FIXED
//                        .requestMatchers(HttpMethod.GET, "/category/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/category", "/category/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")

                        /// USER
                        .requestMatchers(HttpMethod.POST, "/orders/place").authenticated()
                        .requestMatchers(HttpMethod.GET, "/orders/**").authenticated()

                        /// ADMIN (FIXED PATH)
                        .requestMatchers(HttpMethod.POST, "/foods/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/foods/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/foods/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/orders/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                /// ERROR HANDLING
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, ex1) -> {
                            res.setStatus(401);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"message\":\"Unauthorized\"}");
                        })
                        .accessDeniedHandler((req, res, ex2) -> {
                            res.setStatus(403);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"message\":\"Access Denied\"}");
                        })
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}