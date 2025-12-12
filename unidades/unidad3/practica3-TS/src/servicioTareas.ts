import { Tarea, IdTarea, FiltroTarea } from "./models";
import { RepositorioTareasSqlite } from "./repositorioTareasSqlite";

export class ServicioTareas {
  constructor(private repo: RepositorioTareasSqlite) {}

  listar(filtro: FiltroTarea): Tarea[] {
    const todas = this.repo.obtenerTodas();
    switch (filtro) {
      case "pendientes":
        return todas.filter((t) => !t.completada);
      case "completadas":
        return todas.filter((t) => t.completada);
      case "todas":
      default:
        return todas;
    }
  }

  crear(titulo: string, descripcion?: string): Tarea {
    if (!titulo || titulo.trim().length === 0) {
      throw new Error("El título no puede estar vacío");
    }
    return this.repo.crear(titulo, descripcion);
  }

  // EJERCICIO: añade métodos para actualizar, borrar, marcar como completada, etc. Un CRUD de toda la vida
}