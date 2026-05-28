package com.programacion4.ejercicio6.service;

import com.programacion4.ejercicio6.dto.TurnoDTO;
import com.programacion4.ejercicio6.model.Turno;
import com.programacion4.ejercicio6.repository.TurnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;

    

    public TurnoDTO save(TurnoDTO dto) {
        Turno turno = Turno.builder()
                .fecha(dto.getFecha())
                .pacienteUsername(dto.getPacienteUsername())
                .medicoUsername(dto.getMedicoUsername())
                .build();
        
        // Al guardar, el repository devuelve el objeto persistido, nunca nulo
        Turno guardado = turnoRepository.save(turno);
        dto.setId(guardado.getId());
        return dto;
    }

    public List<TurnoDTO> findAll() {
    return turnoRepository.findAll().stream()
            .map(turno -> TurnoDTO.builder()
                    .id(turno.getId())
                    .fecha(turno.getFecha())
                    .pacienteUsername(turno.getPacienteUsername())
                    .medicoUsername(turno.getMedicoUsername())
                    .build())
            .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        // Verificamos antes de borrar para cumplir con la seguridad de tipos
        if (id != null && turnoRepository.existsById(id)) {
            turnoRepository.deleteById(id);
        }
    }

    public boolean isMedicoAsignado(Long turnoId, String medicoUsername) {
        if (turnoId == null || medicoUsername == null) {
            return false;
        }
        
        // Uso de .map() para manejar el Optional de forma segura y funcional
        return turnoRepository.findById(turnoId)
                .map(turno -> medicoUsername.equals(turno.getMedicoUsername()))
                .orElse(false);
    }
}