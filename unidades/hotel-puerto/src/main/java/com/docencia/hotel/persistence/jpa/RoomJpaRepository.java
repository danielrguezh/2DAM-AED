package com.docencia.hotel.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.docencia.hotel.domain.repository.IRoomRepository;
import com.docencia.hotel.model.Room;
import com.docencia.hotel.persistence.jpa.abstracts.AbstractJpaRepository;

import jakarta.transaction.Transactional;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

@Repository
public class RoomJpaRepository extends AbstractJpaRepository<Room, String> {
    
    private final IRoomRepository repository;

    /**
     * Constructor por defecto
     */
    public RoomJpaRepository(IRoomRepository repository) {
        super(Room.class);
        this.repository = repository;
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public Room findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Room> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Room save(Room room) {
        if (room.getId() == null || room.getId().isBlank()) {
            room.setId(UUID.randomUUID().toString());
        }
        return repository.save(room);
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}