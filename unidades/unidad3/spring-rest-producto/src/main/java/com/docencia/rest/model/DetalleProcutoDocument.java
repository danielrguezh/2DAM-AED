package com.docencia.rest.model;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import java.util.Objects;

@Document(collection = "producto_detalle")
public class DetalleProcutoDocument {
    @Id
    private String id;
    
    private int productoId;
    private String descripcionLarga;
    private Map<String, String> especificacionesTecnicas;
    private List<String> tags;


    public DetalleProcutoDocument() {
    }

    public DetalleProcutoDocument(String id, int productoId, String descripcionLarga, Map<String,String> especificacionesTecnicas, List<String> tags) {
        this.id = id;
        this.productoId = productoId;
        this.descripcionLarga = descripcionLarga;
        this.especificacionesTecnicas = especificacionesTecnicas;
        this.tags = tags;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProductoId() {
        return this.productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getDescripcionLarga() {
        return this.descripcionLarga;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public Map<String,String> getEspecificacionesTecnicas() {
        return this.especificacionesTecnicas;
    }

    public void setEspecificacionesTecnicas(Map<String,String> especificacionesTecnicas) {
        this.especificacionesTecnicas = especificacionesTecnicas;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DetalleProcutoDocument)) {
            return false;
        }
        DetalleProcutoDocument detalleProcutoDocument = (DetalleProcutoDocument) o;
        return Objects.equals(id, detalleProcutoDocument.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
