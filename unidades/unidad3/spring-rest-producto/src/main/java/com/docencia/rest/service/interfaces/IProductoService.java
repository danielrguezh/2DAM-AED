package com.docencia.rest.service.interfaces;

import java.util.*;

import com.docencia.rest.model.ProductoEntity;

public interface IProductoService {
    Optional<ProductoEntity> findById(int id);
    Optional<ProductoEntity> find(ProductoEntity producto);
    List<ProductoEntity> findAll();
    ProductoEntity save (ProductoEntity producto);
    boolean deleteById(int id);
   boolean delete(ProductoEntity producto);
}
