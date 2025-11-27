package com.docencia.rest.service.interfaces;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.docencia.rest.model.DetalleProcutoDocument;
import com.docencia.rest.model.ProductoEntity;

public interface DetalleProductoRepository extends MongoRepository <DetalleProcutoDocument,Integer>{
    // Optional<DetalleProcutoDocument> findByProductoId(int productoId);
    // ProductoEntity save(int productoId, DetalleProcutoDocument detalle);
    // void deleteByProductoId(int productoId);
}