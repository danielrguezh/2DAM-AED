package com.docencia.hotel.persistence.jpa;

import com.docencia.hotel.domain.repository.IBookingRepository;
import com.docencia.hotel.model.Booking;
import com.docencia.hotel.persistence.jpa.abstracts.AbstractJpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

@Repository
public class BookingJpaRepository extends AbstractJpaRepository<Booking, String> {

    private final IBookingRepository repository;

    /**
     * Constructor por defecto
     */
    public BookingJpaRepository(IBookingRepository repository) { 
        super(Booking.class); 
        this.repository = repository;
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public Booking findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Booking save(Booking booking) {
        if (booking.getId() == null || booking.getId().isBlank()) {
            booking.setId(UUID.randomUUID().toString());
        }
        return repository.save(booking);
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