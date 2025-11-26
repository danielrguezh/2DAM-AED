package com.docencia.rest.service.interfaces;

import java.util.Optional;

import com.docencia.rest.model.DetalleProcutoDocument;
import com.docencia.rest.model.Producto;

public interface DetalleProductoRepository {
    Optional<DetalleProcutoDocument> findByProductoId(int productoId);
    Producto save(int productoId, DetalleProcutoDocument detalle);
    void deleteByProductoId(int productoId);
}