package com.programacion4.ejercicio6.dto;

import lombok.*;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTO {
    private Long id;

    @NotNull(message = "{turno.fecha.vacia}")
    private Date fecha;
    @NotBlank(message = "{turno.paciente.vacio}")
    private String pacienteUsername;
    @NotBlank(message = "{turno.medico.vacio}")
    private String medicoUsername;
}