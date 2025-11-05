package com.docencia.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "id")
    private String id;


    private String roomId;


    private String guestId;

    
    private String checkIn;

    
    private String checkOut;
}
