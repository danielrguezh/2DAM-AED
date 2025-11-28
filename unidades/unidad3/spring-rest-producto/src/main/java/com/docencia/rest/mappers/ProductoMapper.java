package com.docencia.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.docencia.rest.domain.Producto;
import com.docencia.rest.model.DetalleProcutoDocument;
import com.docencia.rest.model.ProductoEntity;

@Mapper(componentModel = "spring", uses = { DetalleProductoMapper.class })
public interface ProductoMapper {

    // Dominio -> JPA
    ProductoEntity toEntity(Producto producto);

    // JPA -> Dominio (sin detalle)
    Producto toDomain(ProductoEntity entity);

    // JPA + Mongo -> Dominio completo
    @Mapping(target = "id", source = "entity.id")
    // @Mapping(target = "detalleProducto", source = "detalle")
    Producto toDomain(ProductoEntity entity, DetalleProcutoDocument detalle);
}