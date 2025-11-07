package org.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.docencia.Application;
import com.docencia.hotel.domain.repository.IGuestRepository;
import com.docencia.hotel.model.Guest;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class GuestJpaRepositoryTest {
    
    @Autowired
    private IGuestRepository repository;
    private Guest baseGuest;
    
}
