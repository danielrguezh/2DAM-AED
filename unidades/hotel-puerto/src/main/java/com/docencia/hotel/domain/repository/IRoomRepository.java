package com.docencia.hotel.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.docencia.hotel.model.Room;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
@Repository
public interface IRoomRepository extends JpaRepository<Room, String> {
    List<Room> findByHotelId(String hotelId);
}