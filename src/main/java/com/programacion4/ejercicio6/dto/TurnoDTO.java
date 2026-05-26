package com.programacion4.ejercicio6.dto;

import lombok.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTO {
    private Long id;
    private Date fecha;
    private String pacienteUsername;
    private String medicoUsername;
}