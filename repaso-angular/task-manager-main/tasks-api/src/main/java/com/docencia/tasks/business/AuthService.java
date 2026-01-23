package com.docencia.tasks.business;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final String CURRENT_USERNAME = "user";
    private static final String CURRENT_PASSWORD = "password";

    /**
     * Funcion para validar las credenciales de un user
     * @param username del user
     * @param password del user
     * @return true o false
     */
    public boolean credentialValidator(String username, String password) {
        return CURRENT_USERNAME.equals(username) && CURRENT_PASSWORD.equals(password);
    }
}
