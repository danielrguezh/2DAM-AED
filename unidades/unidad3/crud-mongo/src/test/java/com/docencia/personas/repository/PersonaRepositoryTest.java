package com.docencia.personas.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.docencia.personas.model.Direccion;
import com.docencia.personas.model.Persona;
import com.docencia.personas.repository.PersonaRepository;
@SpringBootTest
@ActiveProfiles
public class PersonaRepositoryTest {

    private Persona personaFind;

    private PersonaRepository personaRepository;

    private Persona personaColection;
    private String nombre= "test";
    private int edad= 18;
    private String email= "un@email.com";
    private Direccion direccion;

    private String calle= "una calle";
    private String ciudad= "una ciudad";
    private String codigo= "38200";
    private String pais= "Canarias";

    @Autowired
    public void setPersonaRepository(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @BeforeEach
    void cleanDataBase(){
        personaRepository.deleteAll();
        direccion=new Direccion(calle, ciudad, codigo, pais);
        personaColection = new Persona(nombre, edad, email, direccion);
        personaFind= personaRepository.save(personaColection);
    }

    @Test
    void testFind(){
        Assertions.assertEquals(1, personaRepository.count());
        Assertions.assertNotNull(personaFind);
        Assertions.assertNotNull(personaFind.getId());
    }

    @Test 
    void testFindByCiudad(){
        List<Persona> personasCiudad=personaRepository.findByCiudad(ciudad);
        Assertions.assertEquals(1, personasCiudad.size());
        Assertions.assertEquals(ciudad, personasCiudad.get(0).getDireccion().getCiudad());
    }

    @Test
    void testFindByEdadBetween(){
        int min=17;
        int max=25;
        List<Persona> personasEdad=personaRepository.findByEdadBetween(min, max);
        Assertions.assertEquals(1, personasEdad.size());
        Assertions.assertTrue(personasEdad.get(0).getEdad()>=min && personasEdad.get(0).getEdad()<=max);
    }

    @Test
    void testFindByEmail(){
        Optional<Persona> personaOptional =personaRepository.findByEmail(email);
        Assertions.assertEquals(email, personaOptional.get().getEmail());
    }

    @Test
    void testFindByNombreContainingIgnoreCase(){
        List<Persona> personasNombre=personaRepository.findByNombreContainingIgnoreCase(nombre);
        Assertions.assertEquals(1, personasNombre.size());
        Assertions.assertEquals(nombre, personasNombre.get(0).getNombre());
    }
}
