import { Role, Task } from "../models";

/**
 * E07 – Map / Set
 * @author danielrguezh
 * @version 1.0.0
 */

export function indexTasksById(tasks: Task[]): Map<string, Task> {
  // key=id, value=task (última gana si repetido)

  const map = new Map<string, Task>();
  for (const task of tasks){
    map.set(task.id, task);
  } 
  
  return map;
}

export function uniqueRoles(roles: Role[]): Set<Role> {
  // devuelve Set sin duplicados

  return new Set<Role> (roles);
}

export function touchSession(sessions: Map<string, Date>, token: string, now: Date): Date | undefined {
  // devuelve fecha previa si existía y actualiza token->now
  
  const sesionPrevia = sessions.get(token);
  sessions.set(token, now);
  return sesionPrevia;
}
