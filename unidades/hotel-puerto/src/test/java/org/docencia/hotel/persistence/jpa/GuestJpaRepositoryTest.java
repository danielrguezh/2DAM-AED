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
    
    private String fullName = "Juan Pérez";
    private String email = "juan.perez@example.com";
    private String phone = "987654321";

    // Método que se ejecuta antes de cada prueba
    @BeforeEach
    @Transactional
    void beforeEach() {
        baseGuest = new Guest();
        baseGuest.setFullName("John Doe");  // Inicialización con datos
        baseGuest.setEmail("john.doe@example.com");
        baseGuest.setPhone("123456789");
        baseGuest = repository.save(baseGuest);  // Guardar en la base de datos
    }

    // Prueba de crear y leer un Guest
    @Test
    @Transactional
    void createAndReadGuestTest() {
        String id = baseGuest.getId();

        // Buscar al guest por ID
        Guest guestReadable = repository.findById(id).orElse(null);
        assertThat(guestReadable).isNotNull();

        // Verificar que el guest encontrado tiene el mismo ID
        assertThat(guestReadable.getId()).isEqualTo(id);
    }

    // Prueba de actualizar un Guest
    @Test
    @Transactional
    void updateGuestTest() {
        String id = baseGuest.getId();

        // Actualizar los campos del Guest
        baseGuest.setFullName(fullName);
        baseGuest.setEmail(email);
        baseGuest.setPhone(phone);

        // Guardar el Guest actualizado
        Guest actualizado = repository.save(baseGuest);

        // Verificar que los valores se actualizaron correctamente
        assertThat(actualizado.getFullName()).isEqualTo(fullName);
        assertThat(actualizado.getEmail()).isEqualTo(email);
        assertThat(actualizado.getPhone()).isEqualTo(phone);

        // Volver a leer el Guest y verificar que los cambios se guardaron
        Guest reread = repository.findById(id).orElse(null);
        assertThat(reread.getFullName()).isEqualTo(fullName);
        assertThat(reread.getEmail()).isEqualTo(email);
        assertThat(reread.getPhone()).isEqualTo(phone);
    }

    // Prueba de verificar si un Guest existe por ID
    @Test
    @Transactional
    void checkIfGuestExistsTest() {
        String id = baseGuest.getId();

        // Verificar si el Guest existe
        boolean exists = repository.existsById(id);

        assertThat(exists).isTrue();
    }

    // Prueba de eliminar un Guest
    @Test
    @Transactional
    void deleteGuestTest() {
        String id = baseGuest.getId();

        // Eliminar el Guest
        boolean borrado = repository.deleteById(id);

        // Verificar que el Guest fue eliminado correctamente
        assertThat(borrado).isTrue();
        assertThat(repository.existsById(id)).isFalse();
        assertThat(repository.findById(id)).isNull();
    }

    // Prueba de eliminar un Guest que no existe
    @Test
    @Transactional
    void deleteNonExistingTest() {
        String nonExistingId = "non-existing-id";

        // Intentar eliminar un Guest inexistente
        boolean borrado = repository.deleteById(nonExistingId);

        // Verificar que no se pudo eliminar
        assertThat(borrado).isFalse();
    }
}
