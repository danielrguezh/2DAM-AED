package com.docencia.rest.service.interfaces;

import java.util.*;

import com.docencia.rest.domain.Producto;

public interface IProductoService {
    Optional<Producto> findById(int id);
    Optional<Producto> find(Producto producto);
    List<Producto> findAll();
    Producto save (Producto producto);
    boolean deleteById(int id);
   boolean delete(Producto producto);
}
