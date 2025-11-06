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
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "guest_id")
    private String guestId;

    @Column(name = "check_in")
    private String checkIn;

    @Column(name = "check_out")
    private String checkOut;

    /**
     * Constructor vacio
     */
    public Booking() {
    }

    /**
     * Constructor con identificador
     * @param id
     */
    public Booking(String id) {
        this.id = id;
    }

    /**
     * Constructor con todas las propiedades
     * @param id
     * @param roomId
     * @param guestId
     * @param checkIn
     * @param checkOut
     */
    public Booking(String id, String roomId, String guestId, String checkIn, String checkOut) {
        this.id = id;
        this.roomId = roomId;
        this.guestId = guestId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getGuestId() {
        return this.guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getCheckIn() {
        return this.checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return this.checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(roomId, booking.roomId) && Objects.equals(guestId, booking.guestId) && Objects.equals(checkIn, booking.checkIn) && Objects.equals(checkOut, booking.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, guestId, checkIn, checkOut);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", roomId='" + getRoomId() + "'" +
            ", guestId='" + getGuestId() + "'" +
            ", checkIn='" + getCheckIn() + "'" +
            ", checkOut='" + getCheckOut() + "'" +
            "}";
    }
    
}
