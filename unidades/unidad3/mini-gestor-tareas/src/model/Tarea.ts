/**
 * Representa una tarea del gestor.
 */
export interface Tarea {
  /**
   * Identificador numerico unico.
   */
  id: number;
  /**
   * Titulo breve de la tarea.
   */
  titulo: string;
  /**
   * Detalle opcional de la tarea.
   */
  descripcion?: string;
  /**
   * Indica si la tarea esta completada.
   */
  completada: boolean;
}

export type IdTarea = number;

export type FiltroTarea = "todas" | "pendientes" | "completadas";