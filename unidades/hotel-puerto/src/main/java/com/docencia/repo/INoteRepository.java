package com.docencia.repo;

import com.docencia.model.Note;
import java.util.List;

/**
 * Repositorio para administrar entidades Note.
 *
 * Responsabilidades principales:
 * - Consultar si una nota existe por su id
 * - Recuperar una nota por id
 * - Recuperar una nota usando un ejemplo parcial
 * - Listar todas las notas
 * - Crear o actualizar una nota
 * - Borrar una nota por id
 */
public interface INoteRepository {

    /**
     * Indica si existe una nota con el id dado.
     *
     * @param id identificador unico de la nota
     * @return true si existe una nota con ese id, false en caso contrario
     */
    boolean exists(String id);

    /**
     * Busca una nota por su id.
     *
     * @param id identificador unico de la nota
     * @return la nota encontrada, o null si no existe
     */
    Note findById(String id);

    /**
     * Busca una nota que coincida con los campos relevantes del ejemplo dado.
     * La estrategia concreta depende de la implementacion (por ejemplo,
     * comparar por titulo o contenido).
     *
     * @param example objeto Note usado como ejemplo de busqueda
     * @return una nota que cumpla con el criterio, o null si no hay coincidencia
     */
    Note find(Note example);

    /**
     * Devuelve todas las notas almacenadas.
     *
     * @return lista con todas las notas
     */
    List<Note> findAll();

    /**
     * Inserta o actualiza una nota.
     * - Si la nota no tiene id, la implementacion debe generarlo.
     * - Si la nota ya existe, se actualiza.
     *
     * @param note entidad Note a guardar
     * @return la nota guardada, incluida la informacion final (por ejemplo el id asignado)
     */
    Note save(Note note);

    /**
     * Elimina la nota con el id indicado.
     *
     * @param id identificador unico de la nota
     * @return true si se borro una nota, false si no existia
     */
    boolean delete(String id);
}

