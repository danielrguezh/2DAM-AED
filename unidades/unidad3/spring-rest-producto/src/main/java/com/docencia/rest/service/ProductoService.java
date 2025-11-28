package com.docencia.rest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.docencia.rest.domain.Producto;
import com.docencia.rest.mappers.DetalleProductoMapper;
import com.docencia.rest.mappers.ProductoMapper;
import com.docencia.rest.model.DetalleProcutoDocument;
import com.docencia.rest.model.ProductoEntity;
import com.docencia.rest.repository.ProductoRepository;
import com.docencia.rest.service.interfaces.DetalleProductoRepository;
import com.docencia.rest.service.interfaces.IProductoService;

@Service
public class ProductoService implements IProductoService{
    private ProductoRepository productoRepository;
    private DetalleProductoRepository detalleProductoRepository;
    private ProductoMapper productoMapper;
    private DetalleProductoMapper detalleProductoMapper;

    @Autowired
    public void setProductoRepository(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Autowired
    public void setDetalleProductoRepository(DetalleProductoRepository detalleProductoRepository) {
        this.detalleProductoRepository = detalleProductoRepository;
    }

    @Autowired
    public void setProductoMapper(ProductoMapper productoMapper) {
        this.productoMapper = productoMapper;
    }

    @Autowired
    public void setDetalleProductoMapper(DetalleProductoMapper detalleProductoMapper) {
        this.detalleProductoMapper = detalleProductoMapper;
    }

      

    @Override
    public Optional<Producto> findById(int id) {
        Optional<ProductoEntity> entityOpt = productoRepository.findById(id);
        if (entityOpt.isEmpty()) {
            return Optional.empty();
        }

        ProductoEntity entity = entityOpt.get();
        DetalleProcutoDocument detalleDoc = detalleProductoRepository.findByProductoId(id).orElse(null);

        Producto producto = productoMapper.toDomain(entity, detalleDoc);
        return Optional.of(producto);

    }

    @Override
    public Optional<Producto> find(Producto producto) {
        if (producto == null) {
            return Optional.empty();
        }
        return findById(producto.getId());
    }

    @Override
    public List<Producto> findAll() {
        List<ProductoEntity> entities = productoRepository.findAll();

        return entities.stream()
                .map(entity -> {
                    DetalleProcutoDocument detalleDoc =
                            detalleProductoRepository.findByProductoId(entity.getId()).orElse(null);
                    return productoMapper.toDomain(entity, detalleDoc);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Producto save(Producto producto) {
        ProductoEntity entityToSave = productoMapper.toEntity(producto);
        ProductoEntity savedEntity = productoRepository.save(entityToSave);
        if (producto.getDetalleProducto() == null){
            return productoMapper.toDomain(savedEntity, null);
        }
        DetalleProcutoDocument detalleDoc = detalleProductoMapper.toEntity(producto.getDetalleProducto());
        detalleDoc.setProductoId(savedEntity.getId());
        detalleDoc = detalleProductoRepository.save(detalleDoc);
        return productoMapper.toDomain(savedEntity, detalleDoc);
    }


    @Override
    public boolean deleteById(int id) {
        Producto producto = new Producto(id);
        return delete(producto);
    }


    @Override
    public boolean delete(Producto producto) {
        if (producto == null) {
            return false;
        }
        int id = producto.getId();
        if (!productoRepository.existsById(id)) {
            return false;
        }

        productoRepository.deleteById(id);
        detalleProductoRepository.deleteByProductoId(id);
        return true;
    }

}
