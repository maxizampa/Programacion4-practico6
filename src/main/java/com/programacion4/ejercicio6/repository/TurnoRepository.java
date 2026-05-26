package com.programacion4.ejercicio6.repository;

import com.programacion4.ejercicio6.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
}