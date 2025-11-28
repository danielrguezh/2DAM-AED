package com.docencia.rest.mappers;

import org.mapstruct.Mapper;

import com.docencia.rest.domain.DetalleProducto;
import com.docencia.rest.model.DetalleProcutoDocument;

@Mapper(componentModel = "spring")
public interface DetalleProductoMapper {
    // Dominio -> Mongo
    DetalleProcutoDocument toEntity(DetalleProducto source);

    // Mongo -> Dominio
    DetalleProducto toDomain(DetalleProcutoDocument source);

}
