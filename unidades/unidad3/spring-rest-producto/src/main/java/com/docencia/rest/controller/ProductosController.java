package com.docencia.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docencia.rest.exception.ResourceNotFoundException;
import com.docencia.rest.model.Producto;
import com.docencia.rest.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/productos")
public class ProductosController {
    private ProductoService productoService;

    @Autowired
    public void setProductoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Get all productos")
    @GetMapping("/")
    public List<Producto> getAllProductos(){
        return productoService.findAll();
    }

    @Operation(summary = "Get producto by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Producto not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable(value = "id") int productoId) throws ResourceNotFoundException{
        Producto producto = productoService.findById(productoId).orElse(null);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(producto);
    }

    @Operation(summary = "Delete producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Producto not found")
    })
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteProducto(@PathVariable(value = "id") int productoId) throws ResourceNotFoundException{
        boolean respuesta = productoService.deleteById(productoId);
        Map<String, Boolean> response = new HashMap<>();
        if (!respuesta) {
            response.put("deleted", Boolean.TRUE);
        }
        return response;
    }

    @Operation(summary = "Insert producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/add/user/")
    public Producto createProducto(@Valid @RequestBody Producto producto) {
        return productoService.save(producto);
    }
}
