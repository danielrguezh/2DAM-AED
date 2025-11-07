package com.docencia.hotel.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.docencia.hotel.model.Hotel;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, String> {

}