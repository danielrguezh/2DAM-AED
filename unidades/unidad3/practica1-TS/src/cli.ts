import { crearTarea, filtrarTareas } from "./tareas";
import { Tarea, FiltroTarea } from "./models";

let tareas: Tarea[] = [
  crearTarea(1, "Estudiar TS"),
  crearTarea(2, "Hacer ejercicio"),
  crearTarea(3, "Leer un libro"),
];

const [, , filtroArg] = process.argv;
const filtro: FiltroTarea = (filtroArg as FiltroTarea) ?? "todas";

console.log("Filtro:", filtro);
console.log(filtrarTareas(tareas, filtro));
