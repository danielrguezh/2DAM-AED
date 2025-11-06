package com.docencia.hotel.persistence.jpa;

import com.docencia.hotel.domain.repository.IBookingRepository;
import com.docencia.hotel.model.Booking;
import com.docencia.hotel.persistence.jpa.abstracts.AbstractJpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

@Repository
public class BookingJpaRepository extends AbstractJpaRepository<Booking, String> implements IBookingRepository {

    /**
     * Constructor por defecto
     */
    public BookingJpaRepository() { 
        super(Booking.class); 
    }

    @Override
    public List<Booking> findBookingsByRoomAndDateRange(String roomId, String fromInclusive, String toExclusive) {
        String jpql = "SELECT b FROM Booking b WHERE b.room.id = :roomId "
                    + "AND b.checkIn < :toExclusive AND b.checkOut > :fromInclusive "
                    + "ORDER BY b.checkIn";
        TypedQuery<Booking> querry = entityManager.createQuery(jpql, Booking.class);
        querry.setParameter("roomId", roomId);
        querry.setParameter("fromInclusive", fromInclusive);
        querry.setParameter("toExclusive", toExclusive);
        return querry.getResultList();
    }
}