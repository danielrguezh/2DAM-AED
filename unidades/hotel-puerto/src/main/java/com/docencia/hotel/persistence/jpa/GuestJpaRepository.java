package com.docencia.hotel.persistence.jpa;

import com.docencia.hotel.domain.repository.IGuestRepository;
import com.docencia.hotel.model.Guest;
import com.docencia.hotel.persistence.jpa.abstracts.AbstractJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

@Repository
public class GuestJpaRepository extends AbstractJpaRepository<Guest, String> implements IGuestRepository {

    /**
     * Constructor por defecto
     */
    public GuestJpaRepository() {
        super(Guest.class);
    }
}