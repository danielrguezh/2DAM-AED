import { tareasRemotas } from './apiTareasRemota';
import type { Tarea } from './models';

export type OrigenDatos = 'local' | 'remoto';

export interface RepositorioTareasLocal {
    obtenerTodas(): Promise<Tarea[]>;
    obtenerPorId(id: number): Promise<Tarea | null>;
    crear(tarea: Omit<Tarea, 'id'>): Promise<Tarea>;
    actualizar(id: number, tarea: Partial<Tarea>): Promise<Tarea>;
    eliminar(id: number): Promise<boolean>;
}

export interface ClienteTareasRemoto {
    obtenerTodas(): Promise<Tarea[]>;
    obtenerPorId(id: number): Promise<Tarea>;
    crear(tarea: Omit<Tarea, 'id'>): Promise<Tarea>;
    actualizar(id: number, tarea: Partial<Tarea>): Promise<Tarea>;
    eliminar(id: number): Promise<boolean>;
}

export class servicioTareas {
    constructor(
        private repositorioLocal: RepositorioTareasLocal,
        private clienteRemoto: ClienteTareasRemoto
    ) { }

    async listar(
        origen: OrigenDatos,
        filtro: 'todas' | 'pendientes' | 'completadas' = 'todas'
    ): Promise<Tarea[]> {
        const tareas = origen === 'local'
            ? await this.repositorioLocal.obtenerTodas()
            : await this.clienteRemoto.obtenerTodas();


        switch (filtro) {
            case 'pendientes':
                return tareas.filter(t => !t.completada);
            case 'completadas':
                return tareas.filter(t => t.completada);
            default:
                return tareas;
        }
    }

    async obtenerPorId(
        origen: OrigenDatos,
        id: number
    ): Promise<Tarea | null> {
        if (origen === 'local') {
            return this.repositorioLocal.obtenerPorId(id);
        }
        return this.clienteRemoto.obtenerPorId(id);
    }

    async crear(
        origen: OrigenDatos,
        tarea: Omit<Tarea, 'id'>
    ): Promise<Tarea> {
        return origen === 'local'
            ? await this.repositorioLocal.crear(tarea)
            : await this.clienteRemoto.crear(tarea);
    }

    async actualizar(
        origen: OrigenDatos,
        id: number,
        tarea: Partial<Tarea>
    ): Promise<Tarea> {
        return origen === 'local'
            ? await this.repositorioLocal.actualizar(id, tarea)
            : await this.clienteRemoto.actualizar(id, tarea);
    }

    async eliminar(
        origen: OrigenDatos,
        id: number,
    ): Promise<boolean> {
        return origen === 'local'
            ? await this.repositorioLocal.eliminar(id)
            : await this.clienteRemoto.eliminar(id);
    }

    async sincronizarRemotoALocal(): Promise<void> {
        try {
            const tareasRemotas = await this.clienteRemoto.obtenerTodas();
            const tareasLocales = await this.repositorioLocal.obtenerTodas();
            const mapaLocales = new Map<number, Tarea>();
            tareasLocales.forEach(t => mapaLocales.set(t.id, t));
            for (const tareaRemota of tareasRemotas) {
                const tareaLocal = mapaLocales.get(tareaRemota.id);
                if (!tareaLocal) {
                    await this.repositorioLocal.crear({
                        titulo: tareaRemota.titulo,
                        descripcion: tareaRemota.descripcion,
                        completada: tareaRemota.completada
                    });
                } else {
                    if (
                        tareaLocal.titulo !== tareaRemota.titulo ||
                        tareaLocal.descripcion !== tareaRemota.descripcion ||
                        tareaLocal.completada !== tareaRemota.completada
                    ) {
                        await this.repositorioLocal.actualizar(tareaLocal.id, {
                            titulo: tareaRemota.titulo,
                            descripcion: tareaRemota.descripcion,
                            completada: tareaRemota.completada
                        });
                    }
                }
            }
        } catch (error) {
            console.error('Error durante la sincronización remoto → local', error);
            throw error;
        }
    }
}