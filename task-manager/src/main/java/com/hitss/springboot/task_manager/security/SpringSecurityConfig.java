package com.hitss.springboot.task_manager.security;

import com.hitss.springboot.task_manager.security.filter.JwtAuthenticationFilter;
import com.hitss.springboot.task_manager.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var authFilter = new JwtAuthenticationFilter(authenticationManager());
        authFilter.setFilterProcessesUrl("/api/users/login"); // <-- importantísimo

        var validationFilter = new JwtValidationFilter(authenticationManager());

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // USER y ADMIN pueden leer
                        .requestMatchers(HttpMethod.GET, "/api/tasks", "/api/tasks/{id}")
                        .hasAnyRole("USER","ADMIN")

                        // USER y ADMIN pueden crear
                        .requestMatchers(HttpMethod.POST, "/api/tasks")
                        .hasAnyRole("USER","ADMIN")

                        // Cualquier otra cosa de /api/tasks/** (PUT/DELETE, etc.) solo ADMIN
                        .requestMatchers("/api/tasks/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        // 401: no autenticado
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setStatus(401);
                            res.setContentType("application/json");
                            res.getWriter().write("""
                    {"status":401,"error":"Unauthorized",
                     "message":"Autenticación requerida. Incluye tu token Bearer.",
                     "path":"%s"}
                    """.formatted(req.getRequestURI()));
                        })
                        // 403: autenticado pero sin permisos
                        .accessDeniedHandler((req, res, e) -> {
                            res.setStatus(403);
                            res.setContentType("application/json");
                            res.getWriter().write("""
                    {"status":403,"error":"Forbidden",
                     "message":"Solo ADMIN puede realizar esta operación.",
                     "path":"%s"}
                    """.formatted(req.getRequestURI()));
                        })
                )
                // El validador debe ir antes del UsernamePasswordAuthenticationFilter
                .addFilterBefore(validationFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                // El de autenticación (login) se añade como filtro normal
                .addFilter(authFilter)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(mgmt -> mgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
