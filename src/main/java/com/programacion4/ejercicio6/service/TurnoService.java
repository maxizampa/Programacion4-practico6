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

    public List<TurnoDTO> findAll() {
        return turnoRepository.findAll().stream()
                .map(t -> TurnoDTO.builder()
                        .id(t.getId())
                        .fecha(t.getFecha())
                        .pacienteUsername(t.getPacienteUsername())
                        .medicoUsername(t.getMedicoUsername()).build())
                .collect(Collectors.toList());
    }

    public TurnoDTO save(TurnoDTO dto) {
        Turno turno = Turno.builder()
                .fecha(dto.getFecha())
                .pacienteUsername(dto.getPacienteUsername())
                .medicoUsername(dto.getMedicoUsername())
                .build();
        Turno guardado = turnoRepository.save(turno);
        dto.setId(guardado.getId());
        return dto;
    }

    public void deleteById(Long id) {
        turnoRepository.deleteById(id);
    }

    // metodo @PreAuthorize en el Controller
    public boolean isMedicoAsignado(Long turnoId, String medicoUsername) {
        return turnoRepository.findById(turnoId)
                .map(turno -> turno.getMedicoUsername().equals(medicoUsername))
                .orElse(false);
    }
}