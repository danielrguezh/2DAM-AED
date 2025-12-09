import {
  crearTarea,
  completarTarea,
  filtrarTareas,
} from "./tareas";
import { Tarea } from "./models";

let tareas: Tarea[] = [];

tareas.push(crearTarea(1, "Estudiar TypeScript"));
tareas.push(crearTarea(2, "Hacer la tarea global"));
tareas.push(crearTarea(3, "Descansar un rato"));

tareas = completarTarea(tareas, 1);

console.log("Todas:", tareas);
console.log("Pendientes:", filtrarTareas(tareas, "pendientes"));
console.log("Completadas:", filtrarTareas(tareas, "completadas"));