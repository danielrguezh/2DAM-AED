package org.docencia.hotel.persistence.jpa;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.docencia.hotel.model.Booking;
import com.docencia.hotel.domain.repository.IBookingRepository;

@SpringBootTest
@ActiveProfiles("test")
public class BookingJpaRepositoryTest {

    @Autowired
    private IBookingRepository repository;
    private Booking bookingBase;

    @BeforeEach
    @Transactional
    void beforeEach() {
        Booking booking = new Booking();
        bookingBase = repository.save(booking);
    }
}
