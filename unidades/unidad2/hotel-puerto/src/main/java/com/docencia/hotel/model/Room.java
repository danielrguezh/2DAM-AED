package com.docencia.hotel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "price_per_night", nullable = false)
    private float pricePerNight;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    /**
     * Constructor por defecto
     */
    public Room() {
        this.bookings = new ArrayList<>();
    }

    /**
     * Constructor con identificador
     * @param id
     */
    public Room(String id) {
        this.id = id;
    }

    /**
     * Constructor con todas las propiedades
     * @param id
     * @param number
     * @param type
     * @param pricePerNight
     * @param hotel
     */
    public Room(String id, String number, String type, float pricePerNight, Hotel hotel) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.hotel = hotel;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}