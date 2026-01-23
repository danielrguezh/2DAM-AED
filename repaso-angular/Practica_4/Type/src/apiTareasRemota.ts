import { Tarea } from "./models";

const API_URL = "http://localhost:8080/api/tasks/"

async function handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Error ${response.status}: ${errorText}`);
    }


    if (response.status === 204) {
        return undefined as T;
    }


    return response.json() as Promise<T>;
}

export const tareasRemotas = {
    async obtenerTodas(): Promise<Tarea[]> {
        const response = await fetch(API_URL, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return handleResponse<Tarea[]>(response);
    },

    async obtenerPorId(id: number): Promise<Tarea> {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return handleResponse<Tarea>(response);
    },

    async crear(tarea: Omit<Tarea, 'id'>): Promise<Tarea> {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(tarea),
        });
        return handleResponse<Tarea>(response);
    },

    async actualizar(id: number, tarea: Partial<Tarea>): Promise<Tarea> {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(tarea),
        });
        return handleResponse<Tarea>(response);
    },

    async eliminar(id: number): Promise<boolean> {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        await handleResponse<void>(response);
        return true;
    },
}