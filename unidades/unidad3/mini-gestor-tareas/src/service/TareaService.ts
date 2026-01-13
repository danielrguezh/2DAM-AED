import { FiltroTarea, IdTarea, Tarea } from "../model/Tarea";
import { ITareaRepository } from "../repository/interfaces/ITareaRepository";
import { ITareaService } from "./interfaces/ITareaService";

export class TareaService implements ITareaService {
  constructor(private repo:ITareaRepository) {}

  async listar(filtro: FiltroTarea): Promise<Tarea[]> {
    const todas = await this.repo.obtenerTodas();
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

  async crear(titulo: string, descripcion?: string): Promise<Tarea> {
    if (!titulo || titulo.trim().length === 0) {
      throw new Error("El título no puede estar vacio");
    }
    return await this.repo.crear(titulo, descripcion);
  }

  async actualizar(tarea: Tarea): Promise<Tarea> {
    if (tarea.id === null || tarea.id === undefined) {
      throw new Error("El id de la tarea no es valido");
    }
    if (!tarea.titulo || tarea.titulo.trim().length === 0) {
      throw new Error("El titulo no puede estar vacio");
    }
    if (typeof tarea.completada !== "boolean") {
      throw new Error("El estado de completada debe ser booleano");
    }
    const existente = await this.repo.obtenerPorId(tarea.id);
    if (!existente) {
      throw new Error(`La tarea con id ${tarea.id} no existe`);
    }
    return await this.repo.actualizar(tarea) as Tarea;
  }

  async borrarPorId(id: IdTarea): Promise<boolean> {
    if (id === null || id === undefined) {
      throw new Error("El id de la tarea no es valido");
    }
    const existente = await this.repo.obtenerPorId(id);
    if (!existente) {
      throw new Error(`La tarea con id ${id} no existe`);
    }
    return await this.repo.borrar(id);
  }

  async borrar(tarea: Tarea): Promise<boolean> {
    return this.borrarPorId(tarea.id);
  }
}