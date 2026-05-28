package com.programacion4.ejercicio6.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class AuthDTO {

    @Data @Builder @AllArgsConstructor @NoArgsConstructor
    public static class RegisterRequest {
        @NotBlank(message ="{auth.username.vacio}")
        private String username;
        @NotBlank(message = "{auth.password.vacio}")
        private String password;
    }

    @Data @Builder @AllArgsConstructor @NoArgsConstructor
    public static class LoginRequest {
         @NotBlank(message ="{auth.username.vacio}") private String username;
        @NotBlank(message = "{auth.password.vacio}") private String password;
    }

    @Data @Builder @AllArgsConstructor @NoArgsConstructor
    public static class AuthResponse {
        private String token;
    }

    @Data @Builder @AllArgsConstructor @NoArgsConstructor
    public static class PerfilResponse {
        private String username;
        private String rol;
    }
}