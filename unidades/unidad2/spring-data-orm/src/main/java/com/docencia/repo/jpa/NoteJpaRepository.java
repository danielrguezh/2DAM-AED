package com.docencia.repo.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.docencia.model.Note;
import com.docencia.repo.INoteRepository;

@Repository
public class NoteJpaRepository implements INoteRepository{

    private final ISqliteNoteRepository repository;

    /**
     * Constructor por defecto
     */
    public NoteJpaRepository(){
        this.repository=null;
    }

    /**
     * Constructor con el repository inicializado
     * @param repository de conexion sobre la bbdd
     */
    public NoteJpaRepository(ISqliteNoteRepository repository){
        this.repository=repository;
    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(id);
    }

    @Override
    public Note findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Note find(Note note) {
        return findById(note.getId());
    }

    @Override
    public List<Note> findAll() {
        return repository.findAll();
    }

    @Override
    public Note save(Note note) {
        if (note.getId()==null || StringUtils.isEmpty(note.getId())) {
            note.setId(UUID.randomUUID().toString());
        }
        return repository.save(note);

    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
