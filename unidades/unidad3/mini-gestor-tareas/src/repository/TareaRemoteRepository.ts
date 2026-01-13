import { IdTarea, Tarea } from "../model/Tarea";
import { ITareaRepository } from "./interfaces/ITareaRepository";

const API_URL = "http://localhost:8080/api/tareas";

export class TareaRemoteRepository implements ITareaRepository {

  async obtenerTodas(): Promise<Tarea[]> {
    const response = await fetch(API_URL);
    return response.json();
  }

  async obtenerPorId(id: IdTarea): Promise<Tarea | undefined> {
    const res = await fetch(`${API_URL}/${id}`);
    if (res.status === 404) return undefined;
    return res.json();
  }

  async crear(titulo: string, descripcion?: string): Promise<Tarea> {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ titulo, descripcion }),
    });
    return response.json();
  }

  async actualizar(tarea: Tarea): Promise<Tarea | undefined> {
    const response = await fetch(`${API_URL}/${tarea.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(tarea),
    });
    if (response.status === 404) return undefined;
    return response.json();
  }

  async borrar(id: IdTarea): Promise<boolean> {
    const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    return response.ok;
  }
}
