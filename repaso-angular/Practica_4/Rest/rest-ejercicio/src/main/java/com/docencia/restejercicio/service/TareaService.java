package com.docencia.restejercicio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docencia.restejercicio.model.Tarea;
import com.docencia.restejercicio.repository.TareasRepository;

@Service
public class TareaService {

    private final TareasRepository repository;

    public TareaService(TareasRepository repository) {
        this.repository = repository;
    }

    public List<Tarea> getAll() {
        return repository.findAll();
    }

    public Tarea getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Tarea create(Tarea tarea) {
        return repository.save(tarea);
    }

    public Tarea update(Long id, Tarea update) {
        if (repository.existsById(id)) {
            Tarea oldTarea = getById(id);
            oldTarea.setDescripcion(update.getDescripcion());
            oldTarea.setTitulo(update.getTitulo());
            oldTarea.setCompletada(update.isCompletada());
            return repository.save(oldTarea);
        }
        return null;
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
