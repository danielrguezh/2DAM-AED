import { Task } from "../models";

/**
 * E06 – Intersection + Record
 * @author danielrguezh
 * @version 1.0.0
 */

export type AdminTask = Task & { adminOnly: true };

export function makeAdminTask(task: Task): AdminTask {
  // devuelve task + adminOnly=true (sin mutar task original)

  return{
    ...task,
    adminOnly: true
  };
}

export function buildAuthHeaders(token: string): Record<string, string> {
  // { Authorization: "Bearer <token>", "Content-Type": "application/json" } token trim no vacío
  
  return{
    Authorization: `Bearer ${token.trim()}`,
    "Content-Type": "application/json"
  }
}

export function groupByCompleted(tasks: Task[]): Record<"done" | "pending", Task[]> {
  // retorna { done: [...], pending: [...] } (arrays nuevos)
  
  return{
    done: tasks.filter(task => task.completed),
    pending: tasks.filter(task => !task.completed)
  }
}
