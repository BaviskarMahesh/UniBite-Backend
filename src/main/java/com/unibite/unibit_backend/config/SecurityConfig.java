package com.unibite.unibit_backend.config;

import com.unibite.unibit_backend.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/menu/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/foods/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()

                        .requestMatchers(HttpMethod.POST,"/menu/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/foods/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/foods/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/foods/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/foods/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/categories/**").hasRole("ADMIN")


                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
