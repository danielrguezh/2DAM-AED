import { Tarea, IdTarea, FiltroTarea } from "./models";

/**
 * Crea una nueva tarea a partir de un titulo.
 *
 * @param id Identificador unico de la tarea.
 * @param titulo Titulo visible de la tarea.
 * @param descripcion (Opcional) Detalle adicional.
 * @returns Tarea inicializada como no completada.
 */
export function crearTarea(
  id: IdTarea,
  titulo: string,
  descripcion?: string
): Tarea {
  return {
    id,
    titulo,
    descripcion,
    completada: false,
  };
}

/**
 * Marca una tarea como completada.
 *
 * @param tareas Lista original de tareas.
 * @param id Identificador de la tarea a completar.
 * @returns Nueva lista de tareas con la tarea marcada.
 */
export function completarTarea(tareas: Tarea[], id: IdTarea): Tarea[] {
  return tareas.map((t) =>
    t.id === id ? { ...t, completada: true } : t
  );
}

/**
 * Filtra las tareas segun el filtro indicado.
 *
 * @param tareas Lista original de tareas.
 * @param filtro "todas", "pendientes" o "completadas".
 * @returns Lista filtrada de tareas.
 */
export function filtrarTareas(
  tareas: Tarea[],
  filtro: FiltroTarea
): Tarea[] {
  if (filtro === "pendientes") {
    return tareas.filter((t) => !t.completada);
  } else if (filtro === "completadas") {
    return tareas.filter((t) => t.completada);
  }
  return tareas;
}