package com.docencia.restejercicio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docencia.restejercicio.model.Tarea;
import com.docencia.restejercicio.service.TareaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Operaciones sobre tareas")
public class TaskController {
    private TareaService tareaService;

    @Autowired
    public void setTareaService(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    /**
     * endpoint donde se va a devolver todos los Tareas
     * @return Lista de Tareas
     */
    @Operation(summary="Get all Tareas")
    @GetMapping("/")
    public List<Tarea> getAllTareas(){
        return tareaService.getAll();
    }

    /**
     * Endpoint donde se devolvera un tarea segun su id
     * @param userId Id a buscar
     * @return tarea con el id indicado
     */
    @Operation(summary="Get Tarea with Id")
    @GetMapping("/{id}")
    public Tarea getTareasById(@PathVariable(value = "id") Long tareaId){
        return tareaService.getById(tareaId);
    }

    /**
     * Endpoint de creacion de Tarea
     * @param user Tarea a crear
     * @return Tarea Creado
     */
    @Operation(summary="Add a Tarea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/")
    public Tarea createTarea(@Valid @RequestBody Tarea tarea){
        return tareaService.create(tarea);
    }

    /**
     * Endpoint de modificacion de Tarea
     * @param userId Id del Tarea a modificar
     * @param user Modificaciones que se quieren hacer
     * @return Tarea modificado
     */
    @Operation(summary = "Update Tarea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea updated successfully"),
            @ApiResponse(responseCode = "404", description = "Tarea not found")
    })
    @PutMapping("/{id}")
    public Tarea updateTarea(@PathVariable(value = "id") Long tareaId,
                                          @Valid @RequestBody Tarea tarea) {
        return tareaService.update(tareaId, tarea);
    }

    /**
     * Endpoint para borrar una tarea
     * @param userId Id de la tarea a eliminar
     * @return Una respuesta con deleted y si se realizo la operacion o no
     */
    @Operation(summary = "Delete Tarea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Tarea not found")
    })
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteTarea(@PathVariable(value = "id") Long tareaId){
        boolean respuesta = tareaService.delete(tareaId); 
        Map<String, Boolean> response = new HashMap<>();
        if (respuesta == false) {
            response.put("deleted", Boolean.FALSE);
            return response;
        }
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
