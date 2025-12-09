"use strict";
var _a;
Object.defineProperty(exports, "__esModule", { value: true });
const tareas_1 = require("./tareas");
let tareas = [
    (0, tareas_1.crearTarea)(1, "Estudiar TS"),
    (0, tareas_1.crearTarea)(2, "Hacer ejercicio"),
    (0, tareas_1.crearTarea)(3, "Leer un libro"),
];
const [, , filtroArg] = process.argv;
const filtro = (_a = filtroArg) !== null && _a !== void 0 ? _a : "todas";
console.log("Filtro:", filtro);
console.log((0, tareas_1.filtrarTareas)(tareas, filtro));
