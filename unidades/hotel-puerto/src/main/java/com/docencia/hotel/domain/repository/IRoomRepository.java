package com.docencia.hotel.domain.repository;

import java.util.List;

import com.docencia.hotel.domain.repository.interfaces.ICrudRepository;
import com.docencia.hotel.model.Room;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public interface IRoomRepository extends ICrudRepository<Room> {
    List<Room> findByHotelId(String hotelId);
}