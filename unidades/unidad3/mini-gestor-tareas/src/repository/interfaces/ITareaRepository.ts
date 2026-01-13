import { IdTarea, Tarea } from "../../model/Tarea";

export interface ITareaRepository {
  obtenerTodas(): Promise<Tarea[]>;
  obtenerPorId(id: IdTarea): Promise<Tarea | undefined>;
  crear(titulo: string, descripcion?: string): Promise<Tarea>;
  actualizar(tarea: Tarea): Promise<Tarea | undefined>;
  borrar(id: IdTarea): Promise<boolean>;
}
