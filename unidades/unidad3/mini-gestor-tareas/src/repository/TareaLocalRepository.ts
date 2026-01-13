import { getDb } from "../db/db";
import { IdTarea, Tarea } from "../model/Tarea";
import { ITareaRepository } from "./interfaces/ITareaRepository";

export class TareaLocalRepository implements ITareaRepository{

  private db = getDb();

  async obtenerTodas(): Promise<Tarea[]> {
    const tareas = this.db.prepare("SELECT id, titulo, descripcion, completada FROM tareas")
      .all() as Tarea[];
    return tareas.map(t => ({
      id: t.id,
      titulo: t.titulo,
      descripcion: t.descripcion,
      completada: Boolean(t.completada),
    }));
  }

  async obtenerPorId(id: IdTarea): Promise<Tarea | undefined> {
    const tarea = this.db.prepare("SELECT id, titulo, descripcion, completada FROM tareas WHERE id = ?")
      .get(id) as Tarea | undefined;
    if (!tarea) {
      return undefined;
    }
    return {
      id: tarea.id,
      titulo: tarea.titulo,
      descripcion: tarea.descripcion,
      completada: Boolean(tarea.completada),
    }
  }

  async crear(titulo: string, descripcion?: string): Promise<Tarea> {
    const stmt = this.db.prepare("INSERT INTO tareas (titulo, descripcion, completada) VALUES (?, ?, 0)");
    const info = stmt.run(titulo, descripcion ?? null);
    return {
      id: Number(info.lastInsertRowid),
      titulo,
      descripcion,
      completada: false,
    };
  }

  async actualizar(tarea: Tarea): Promise<Tarea | undefined> {
    const stmt = this.db.prepare("UPDATE tareas SET titulo = ?, descripcion = ?, completada = ? WHERE id = ?");
    const info = stmt.run(tarea.titulo, tarea.descripcion ?? null, tarea.completada ? 1 : 0, tarea.id);
    if (info.changes === 0) {
      return undefined;
    }
    return tarea;
  }

  async borrar(id: IdTarea): Promise<boolean> {
    const stmt = this.db.prepare("DELETE FROM tareas WHERE id = ?");
    const info = stmt.run(id);
    return info.changes > 0;
  }
}