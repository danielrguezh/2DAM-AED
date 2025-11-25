package org.docencia.hotel.persistence.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.docencia.hotel.domain.repository.IRoomRepository;
import com.docencia.hotel.model.Room;

@SpringBootTest
@ActiveProfiles("test")
public class RoomJpaRepositoryTest {

    @Autowired
    private IRoomRepository repository;
    private Room baseRoom;

    @BeforeEach
    @Transactional
    void beforeEach() {
        Room room = new Room();
        baseRoom = repository.save(room);
    }
}
