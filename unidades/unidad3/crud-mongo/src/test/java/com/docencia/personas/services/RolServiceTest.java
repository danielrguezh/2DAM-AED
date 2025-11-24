package com.docencia.personas.services;



import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.docencia.personas.model.Rol;
import com.docencia.personas.services.interfaces.IRolService;

@SpringBootTest
public class RolServiceTest {
    private static final String ROL_NOMBRE = "rol nombre";
    private static final String ROL_1 = "ROL-1";
    IRolService rolService;
    Rol rol;

    @Autowired
    public void setRolService(IRolService rolService) {
        this.rolService = rolService;
    }

    @BeforeEach
    void BeforeEach(){
        if (rol == null) {
            rol=new Rol(ROL_1, ROL_NOMBRE);
        }
        rolService.save(rol);
    }

    @Test 
    void findByTest(){
        Rol rolFind=new Rol(ROL_1);
        rolFind=rolService.findBy(rolFind);
        Assertions.assertNotNull(rolFind.getId());
        Assertions.assertEquals(rol, rolFind);
        Assertions.assertEquals(ROL_1, rolFind.getId());
        Assertions.assertEquals(ROL_NOMBRE, rolFind.getNombre());
    }

    @AfterEach
    void AfterEach(){
        Rol rolDelete=new Rol(ROL_1);
        rolService.delete(rolDelete);

        rolDelete=rolService.findBy(rolDelete);
        Assertions.assertNull(rolDelete);
    }
}
