package com.docencia.restejercicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.docencia.restejercicio.model.Tarea;

@Repository
public interface TareasRepository extends JpaRepository<Tarea, Long> {
}
