// src/index.ts
import { RepositorioTareasSqlite } from "./repositorioTareasSqlite";
import { ServicioTareas } from "./servicioTareas";

async function main() {
  const repo = new RepositorioTareasSqlite();
  const servicio = new ServicioTareas(repo);

  console.log("Tareas actuales:");
  console.log(servicio.listar("todas"));

  console.log("Creando una nueva tarea...");
  const nueva = servicio.crear("Aprender SQLite3 con TypeScript", "Práctica 3");
  console.log("Tarea creada:", nueva);

  console.log("Tareas tras la creación:");
  console.log(servicio.listar("todas"));
}

main().catch((error) => {
  console.error("Error en main:", error);
});