package com.docencia.rest.service.interfaces;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.docencia.rest.model.DetalleProcutoDocument;

public interface DetalleProductoRepository extends MongoRepository <DetalleProcutoDocument,Integer>{
    Optional<DetalleProcutoDocument> findByProductoId(int productoId);
    void deleteByProductoId(int productoId);
}