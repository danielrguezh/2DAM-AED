package org.docencia.hotel.persistence.jpa;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.docencia.hotel.domain.repository.IHotelRepository;
import com.docencia.hotel.model.Hotel;

@SpringBootTest
@ActiveProfiles("test")
public class HotelJpaRepositoryTest {

    @Autowired
    private IHotelRepository repository;
    private Hotel baseHotel;

    @BeforeEach
    @Transactional
    void beforeEach() {
        Hotel hotel = new Hotel();
        baseHotel = repository.save(hotel);
    }
}
