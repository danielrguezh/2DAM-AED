package com.docencia.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

@Entity
@Table(name = "hotel")
public class Room {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "number")
    private String number;
    
    @Column(name = "type")
    private String type;

    @Column(name = "prince_per_night")
    private float princePerNight;

    @Column(name = "hotel_id")
    private String hotelId;

    /**
     * Constructor vacio
     */
    public Room() {
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
     * @param princePerNight
     * @param hotelId
     */
    public Room(String id, String number, String type, float princePerNight, String hotelId) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.princePerNight = princePerNight;
        this.hotelId = hotelId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrincePerNight() {
        return this.princePerNight;
    }

    public void setPrincePerNight(float princePerNight) {
        this.princePerNight = princePerNight;
    }

    public String getHotelId() {
        return this.hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(number, room.number) && Objects.equals(type, room.type) && princePerNight == room.princePerNight && Objects.equals(hotelId, room.hotelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, type, princePerNight, hotelId);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", number='" + getNumber() + "'" +
            ", type='" + getType() + "'" +
            ", princePerNight='" + getPrincePerNight() + "'" +
            ", hotelId='" + getHotelId() + "'" +
            "}";
    }
    
}
