package com.programacion4.ejercicio6.service;

import com.programacion4.ejercicio6.dto.AuthDTO.*;

import com.programacion4.ejercicio6.model.Usuario;
import com.programacion4.ejercicio6.repository.UsuarioRepository;
import com.programacion4.ejercicio6.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registrar(RegisterRequest request) {
    
    if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
        throw new IllegalArgumentException("El nombre de usuario ya está en uso");
    }
    //habria que envolver el usuario en un objeto? ver
    Usuario user = Usuario.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .rol("ROLE_PACIENTE")
            .build();

    // Guardado en h2
    usuarioRepository.save(user);

    // token
    var jwtToken = jwtService.generateToken(user);
    return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public PerfilResponse obtenerPerfil(String username) {
        var user = usuarioRepository.findByUsername(username).orElseThrow();
        return PerfilResponse.builder()
                .username(user.getUsername())
                .rol(user.getRol())
                .build();
    }
}
