package com.docencia.objetos.domain;
import java.util.Objects;

// TODO: añadir validación (jakarta.validation) si se desea
public class Alumno {
  // TODO: definir campos y encapsulación
  private Long id;
  private String nombre;
  private String email;
  private String ciclo;

  /**
   * Constructor vacio
   */
  public Alumno() {
  }

  /**
   * Constructor con identificador
   * @param id del alumno
   */
  public Alumno(Long id) {
    this.id = id;
  }

  /**
   * Constructor con todas las propiedades
   * @param id
   * @param nombre
   * @param email
   * @param ciclo
   */
  public Alumno(Long id, String nombre, String email, String ciclo) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
    this.ciclo = ciclo;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCiclo() {
    return this.ciclo;
  }

  public void setCiclo(String ciclo) {
    this.ciclo = ciclo;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Alumno)) {
            return false;
        }
        Alumno alumno = (Alumno) o;
        if (id == null || alumno.getId() == null) {
          return false;
        }
        return Objects.equals(id, alumno.id);
      }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", nombre='" + getNombre() + "'" +
      ", email='" + getEmail() + "'" +
      ", ciclo='" + getCiclo() + "'" +
      "}";
  }
  
}
