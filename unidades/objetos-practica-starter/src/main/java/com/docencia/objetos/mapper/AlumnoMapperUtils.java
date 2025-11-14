package com.docencia.objetos.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.docencia.objetos.domain.Alumno;
import com.docencia.objetos.repo.jpa.AlumnoEntity;

public class AlumnoMapperUtils {
    /**
     * Funcion que transforma un objeto de tipo alumnoEntity a alumno
     * @param alumnoEntity como entrada
     * @return alumno convertido
     */
    public static Alumno to(AlumnoEntity alumnoEntity){
        if (alumnoEntity==null) {
            return null;
        }
        Alumno alumno = new Alumno(alumnoEntity.getId(),alumnoEntity.getNombre(),alumnoEntity.getEmail(),alumnoEntity.getCiclo());
        return alumno;
    }

    /**
     * Funcion que transforma un objeto de tipo alumno a alumnoEntity
     * @param alumno como entrada
     * @return alumnoEntity convertido
     */
    public static AlumnoEntity to(Alumno alumno){
        if (alumno==null) {
            return null;
        }
        AlumnoEntity alumnoEntity = new AlumnoEntity(alumno.getId(),alumno.getNombre(),alumno.getEmail(),alumno.getCiclo());
        return alumnoEntity;
    }

    /**
     * Funcion que transforma una lista de objetos de tipo alumnoEntity a alumno
     * @param alumnoEntities como entrada
     * @return lista de alumnos convertida
     */
    public static List<Alumno> to(List<AlumnoEntity> alumnoEntities){
        List<Alumno> alumnos = new ArrayList<>();
        if (alumnoEntities==null || alumnoEntities.isEmpty()) {
            return alumnos;
        }
        for (AlumnoEntity alumnoEntity : alumnoEntities) {
            alumnos.add(to(alumnoEntity));
        }
        return alumnos;
    }

    public static Optional<Alumno> to(Optional<AlumnoEntity> alumnoEntity) {
        Optional<Alumno> resultado = java.util.Optional.empty();
        if (alumnoEntity==null || alumnoEntity.isEmpty()) {
            return resultado;
        }
        return resultado.map(alumno -> to(alumnoEntity)).orElse(null);
    }
}
