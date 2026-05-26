package com.programacion4.ejercicio6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice // Le indica a Spring que capturará errores transversalmente en todos los @RestController
public class GlobalExceptionHandler {

    /**
     * 1. Captura fallos de validación en los DTOs (@Valid / @Validated)
     * Cuando una validación falla, devuelve un 400 Bad Request junto con un mapa ordenado: { "campo": "mensaje" }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_EMPTY == null ? HttpStatus.BAD_REQUEST : HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * 2. Captura cuando se buscan elementos en BD que no existen (ej: findByUsername().orElseThrow())
     * Devuelve un código HTTP 404 Not Found con un JSON plano descriptivo.
     */
    @ExceptionHandler({NoSuchElementException.class, RuntimeException.class})
    public ResponseEntity<Map<String, String>> handleNotFoundExceptions(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * 3. Captura fallos de credenciales en la Autenticación (Login Erróneo)
     * Devuelve un código HTTP 401 Unauthorized para evitar dar pistas de más al atacante.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Usuario o contraseña incorrectos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
