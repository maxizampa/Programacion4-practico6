package com.programacion4.ejercicio6.controller;

import com.programacion4.ejercicio6.dto.AuthDTO.PerfilResponse;
import com.programacion4.ejercicio6.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final AuthService authService;

    // Req 7: Retorna exclusivamente la sesión del paciente actual de forma inviolable
    @GetMapping("/me")
    public ResponseEntity<PerfilResponse> getMyProfile(Authentication authentication) {
        String currentUsername = authentication.getName();
        return ResponseEntity.ok(authService.obtenerPerfil(currentUsername));
    }
}