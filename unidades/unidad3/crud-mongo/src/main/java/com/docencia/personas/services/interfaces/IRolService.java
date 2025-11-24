package com.docencia.personas.services.interfaces;

import com.docencia.personas.model.Rol;

public interface IRolService {
    public Rol save(Rol rol);
    public Rol findBy(Rol rol);
    public boolean delete (Rol rol);
}
