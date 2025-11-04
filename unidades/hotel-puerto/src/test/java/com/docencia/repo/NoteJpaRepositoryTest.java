package com.docencia.repo;

import com.docencia.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class NoteJpaRepositoryTest {

    @Autowired
    private INoteRepository noteRepository;

    private Note baseNote; 
    
    @BeforeEach
    @Transactional
    void beforeEach() {
        Note n = new Note();
        n.setTitle("Nota base");
        n.setContent("contenido base");

        baseNote = noteRepository.save(n);

        assertThat(baseNote.getId()).isNotNull();
    }

    @Test
    @Transactional
    void createReadFindByTitleTest() {
        String id = baseNote.getId();

        Note leida = noteRepository.findById(id);
        assertThat(leida).isNotNull();
        assertThat(leida.getTitle()).isEqualTo("Nota base");
        assertThat(leida.getContent()).isEqualTo("contenido base");

        Note buscadaPorTitulo = noteRepository.find(leida);
        assertThat(buscadaPorTitulo).isNotNull();
        assertThat(buscadaPorTitulo.getId()).isEqualTo(id);
    }

    @Test
    @Transactional
    void updateContentTest() {
        String id = baseNote.getId();

        baseNote.setContent("contenido modificado");
        Note actualizada = noteRepository.save(baseNote);

        assertThat(actualizada.getContent())
                .isEqualTo("contenido modificado");

        Note reread = noteRepository.findById(id);
        assertThat(reread.getContent())
                .isEqualTo("contenido modificado");
    }

    @Test
    @Transactional
    void findByIdTest() {
        String id = baseNote.getId();

        boolean exists = noteRepository.exists(id);

        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    void deleteNoteTest() {
        String id = baseNote.getId();

        boolean borrada = noteRepository.delete(id);

        assertThat(borrada).isTrue();
        assertThat(noteRepository.exists(id)).isFalse();
        assertThat(noteRepository.findById(id)).isNull();
    }
}
