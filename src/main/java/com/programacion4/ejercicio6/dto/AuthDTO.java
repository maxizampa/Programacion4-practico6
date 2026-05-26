package com.programacion4.ejercicio6.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class AuthDTO {

    @Data @Builder @AllArgsConstructor @NoArgsConstructor
    public static class RegisterRequest {
        @NotBlank(message = "El nombre de usuario es obligatorio")
        private String username;
        @NotBlank(message = "La contraseña es obligatoria")
        private String password;
    }

    @Data @Builder @AllArgsConstructor @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank private String username;
        @NotBlank private String password;
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