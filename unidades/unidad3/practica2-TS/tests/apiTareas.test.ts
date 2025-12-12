import {
  obtenerTareas,
  crearTareaRemota,
  actualizarTareaRemota,
  borrarTareaRemota,
} from "../src/apiTareas";
import { Tarea } from "../src/models";

// Hacemos que TypeScript sepa que vamos a mockear fetch
declare const global: typeof globalThis & {
  fetch: jest.Mock;
};

beforeEach(() => {
  global.fetch = jest.fn();
});

describe("apiTareas (servicio REST)", () => {
  test("obtenerTareas hace un GET y devuelve la lista de tareas", async () => {
    const tareasMock: Tarea[] = [
      { id: 1, titulo: "A", completada: false },
      { id: 2, titulo: "B", completada: true },
    ];

    global.fetch.mockResolvedValue({
      ok: true,
      status: 200,
      statusText: "OK",
      json: async () => tareasMock,
    });

    const resultado = await obtenerTareas();

    expect(global.fetch).toHaveBeenCalledTimes(1);
    expect(global.fetch).toHaveBeenCalledWith("http://localhost:3000/tareas");
    expect(resultado).toEqual(tareasMock);
  });

  test("crearTareaRemota hace POST y devuelve la tarea creada", async () => {
    const tareaCreada: Tarea = {
      id: 10,
      titulo: "Nueva tarea",
      completada: false,
    };

    global.fetch.mockResolvedValue({
      ok: true,
      status: 201,
      statusText: "Created",
      json: async () => tareaCreada,
    });

    const resultado = await crearTareaRemota({
      titulo: "Nueva tarea",
      completada: false,
    });

    expect(global.fetch).toHaveBeenCalledTimes(1);
    const [url, opciones] = global.fetch.mock.calls[0];
    expect(url).toBe("http://localhost:3000/tareas");
    expect(opciones.method).toBe("POST");
    expect(resultado).toEqual(tareaCreada);
  });

  test("actualizarTareaRemota hace PUT y devuelve la tarea actualizada", async () => {
    const tareaActualizada: Tarea = {
      id: 1,
      titulo: "Actualizada",
      completada: true,
    };

    global.fetch.mockResolvedValue({
      ok: true,
      status: 200,
      statusText: "OK",
      json: async () => tareaActualizada,
    });

    const resultado = await actualizarTareaRemota(tareaActualizada);

    expect(global.fetch).toHaveBeenCalledTimes(1);
    const [url, opciones] = global.fetch.mock.calls[0];
    expect(url).toBe("http://localhost:3000/tareas/1");
    expect(opciones.method).toBe("PUT");
    expect(resultado).toEqual(tareaActualizada);
  });

  test("borrarTareaRemota hace DELETE y no devuelve contenido", async () => {
    global.fetch.mockResolvedValue({
      ok: true,
      status: 200,
      statusText: "OK",
      json: async () => ({}),
    });

    await borrarTareaRemota(1);

    expect(global.fetch).toHaveBeenCalledTimes(1);
    const [url, opciones] = global.fetch.mock.calls[0];
    expect(url).toBe("http://localhost:3000/tareas/1");
    expect(opciones.method).toBe("DELETE");
  });

  test("obtenerTareas lanza error si la respuesta no es ok", async () => {
    global.fetch.mockResolvedValue({
      ok: false,
      status: 500,
      statusText: "Internal Server Error",
      json: async () => [],
    });

    await expect(obtenerTareas()).rejects.toThrow("Error al cargar tareas");
  });
});