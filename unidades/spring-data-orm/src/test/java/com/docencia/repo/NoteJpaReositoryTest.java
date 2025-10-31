package com.docencia.repo;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class NoteJpaReositoryTest {
    @Autowired
    private INoteRepository noteRepository;

    @Test
    void buscarNotaTest(){
        Assertions.assertNotNull(noteRepository);
    }
}
