package com.programacion4.ejercicio6.config;

import com.programacion4.ejercicio6.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //habilita uso de preauthorize en los controllers
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // rutas swagger (incluye swagger-config e index)
                .requestMatchers(
                    "/v3/api-docs",                // Ruta exacta
                    "/v3/api-docs/**",             // Subrutas
                    "/v3/api-docs/swagger-config", // Configuración usada por Swagger UI
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-ui/index.html",
                    "/swagger-resources/**",       // Recursos internos
                    "/webjars/**"                  // Estilos y scripts
                ).permitAll()
                
                .requestMatchers("/h2-console/**").permitAll()
                
                //endpoint autenticacion publico
                .requestMatchers("/api/auth/**").permitAll()
                
                //filtro por rol
                .requestMatchers(HttpMethod.GET, "/api/turnos").hasAnyRole("MEDICO", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/pacientes/me").hasRole("PACIENTE")
                
                
                .anyRequest().authenticated()
            );

        // consola h2
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}