package com.docencia.hotel.persistence.interfaces;

import java.util.List;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public interface ICrudRepository<T, ID> {
    
    /**
     * Metodo que indica si existe una entity con el id dado.
     * @param id identificador unico de la entity
     * @return true/false
     */
    boolean existsById(ID id);

    /**
     * Metodo que busca una entity por su id.
     * @param id identificador unico de la entity
     * @return la entity encontrada
     */
    T findById(ID id);

    /**
     * Devuelve todas las entities almacenadas.
     * @return lista con todas las entities
     */
    List<T> findAll();

    /**
     * Inserta o actualiza una entity.
     * - Si la entity no tiene id, la implementacion debe generarlo.
     * - Si la entity ya existe, se actualiza.
     * @param entity a guardar
     * @return la entity guardada, incluida la informacion final 
     * (por ejemplo el id asignado)
     */
    T save(T entity);

    /**
     * Elimina la entity con el id indicado.
     * @param id identificador unico de la entity
     * @return true si se borro la entity, false si no existia
     */
    boolean deleteById(ID id);
}