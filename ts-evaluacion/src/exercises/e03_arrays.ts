import { Task } from "../models";

/**
 * E03 – Arrays: filter/map/reduce/sort sin mutar original
 * @author danielrguezh
 * @version 1.0.0
 */

export function pendingTasks(tasks: Task[]): Task[] {
  // retorna nuevas tareas con completed=false

  return tasks.filter(task => !task.completed);
}

export function titlesSorted(tasks: Task[]): string[] {
  // devuelve títulos ordenados alfabéticamente (localeCompare), sin mutar tasks
  const titles = tasks.map(task => task.title);
  return titles.sort((a, b) => a.localeCompare(b));
}

export function completionPercent(tasks: Task[]): number {
  // % completadas 0..100 redondeado; si vacío => 0
  if(tasks == null || tasks.length == 0){
    return 0;
  }

  return (tasks.length - pendingTasks(tasks).length) / tasks.length * 100;
}
