package com.programacion4.ejercicio6.controller;

import com.programacion4.ejercicio6.dto.TurnoDTO;
import com.programacion4.ejercicio6.service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> getAllTurnos() {
        return ResponseEntity.ok(turnoService.findAll());
    }

    // Req 5: Solo un PACIENTE puede crear un turno para sí mismo
    @PostMapping
    @PreAuthorize("hasRole('PACIENTE') and #dto.pacienteUsername == authentication.name")
    public ResponseEntity<TurnoDTO> createTurno(@RequestBody TurnoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.save(dto));
    }

    // Req 6: Solo ADMIN o el MEDICO que se encuentra asignado a dicho ID de turno
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEDICO') and @turnoService.isMedicoAsignado(#id, authentication.name))")
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        turnoService.deleteById(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }
}