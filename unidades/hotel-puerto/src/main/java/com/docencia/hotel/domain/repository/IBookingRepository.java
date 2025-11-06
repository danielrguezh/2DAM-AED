package com.docencia.hotel.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.docencia.hotel.model.Booking;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public interface IBookingRepository extends JpaRepository<> {
    List<Booking> findBookingsByRoomAndDateRange(String roomId, String fromInclusive, String toExclusive);
}