import { Tarea, IdTarea, FiltroTarea } from "../../model/Tarea";

export interface ITareaService {
    listar(filtro: FiltroTarea): Promise<Tarea[]>;
    crear(titulo: string, descripcion?: string): Promise<Tarea>;
    actualizar(tarea: Tarea): Promise<Tarea>;
    borrarPorId(id: IdTarea): Promise<boolean>;
    borrar(tarea: Tarea): Promise<boolean>;
}