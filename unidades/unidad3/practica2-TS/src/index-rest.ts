import { obtenerTareas, crearTareaRemota } from "./apiTareas";
import { Tarea } from "./models";

async function main() {
  console.log("Cargando tareas desde la API...");
  const tareas: Tarea[] = await obtenerTareas();
  console.log("Tareas iniciales:", tareas);

  console.log("Creando una nueva tarea remota...");
  const nueva = await crearTareaRemota({
    titulo: "Tarea creada desde index-rest.ts",
    descripcion: "Probando POST contra json-server",
    completada: false,
  });

  console.log("Tarea creada:", nueva);

  const tareasActualizadas = await obtenerTareas();
  console.log("Tareas tras la creación:", tareasActualizadas);
}

main().catch((error) => {
  console.error("Error en main:", error);
});