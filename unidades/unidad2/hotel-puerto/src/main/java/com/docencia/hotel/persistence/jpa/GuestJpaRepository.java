package com.docencia.hotel.persistence.jpa;

import com.docencia.hotel.domain.repository.IGuestRepository;
import com.docencia.hotel.model.Guest;
import com.docencia.hotel.persistence.jpa.abstracts.AbstractJpaRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

@Repository
public class GuestJpaRepository extends AbstractJpaRepository<Guest, String> {

    private final IGuestRepository repository;

    /**
     * Constructor por defecto
     */
    public GuestJpaRepository(IGuestRepository repository) {
        super(Guest.class);
        this.repository = repository;
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public Guest findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Guest> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Guest save(Guest guest) {
        if (guest.getId() == null || guest.getId().isBlank()) {
            guest.setId(UUID.randomUUID().toString());
        }
        return repository.save(guest);
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