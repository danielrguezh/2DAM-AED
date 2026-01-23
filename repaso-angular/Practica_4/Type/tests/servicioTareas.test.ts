import { servicioTareas, OrigenDatos } from '../src/servicioTareas';
import { Tarea } from '../src/models';


// Mocks
class MockRepoLocal {
    private tareas: Tarea[] = [];
    async obtenerTodas() { return this.tareas; }
    async obtenerPorId(id: number) { return this.tareas.find(t => t.id === id) || null; }
    async crear(tarea: Omit<Tarea, 'id'>) {
        const newT: Tarea = { id: Date.now(), ...tarea };
        this.tareas.push(newT);
        return newT;
    }
    async actualizar(id: number, tarea: Partial<Tarea>): Promise<Tarea> {
        const index = this.tareas.findIndex(t => t.id === id);
        if (index === -1) throw new Error('No existe');
        const updated = { ...this.tareas[index], ...tarea };
        this.tareas[index] = updated;
        return updated;
    }

    async eliminar(id: number) { return true; }
}


class MockClienteRemoto {
    private tareas: Tarea[] = [];

    async obtenerTodas(): Promise<Tarea[]> { return this.tareas; }

    async obtenerPorId(id: number): Promise<Tarea> {
        const tarea = this.tareas.find(t => t.id === id);
        if (!tarea) throw new Error('Tarea no encontrada'); // nunca retorna null
        return tarea;
    }

    async crear(tarea: Omit<Tarea, 'id'>): Promise<Tarea> {
        const newT: Tarea = { id: Date.now(), ...tarea };
        this.tareas.push(newT);
        return newT;
    }

    async actualizar(id: number, tarea: Partial<Tarea>): Promise<Tarea> {
        const index = this.tareas.findIndex(t => t.id === id);
        if (index === -1) throw new Error('Tarea no encontrada');
        const updated = { ...this.tareas[index], ...tarea };
        this.tareas[index] = updated;
        return updated;
    }

    async eliminar(id: number): Promise<boolean> {
        const index = this.tareas.findIndex(t => t.id === id);
        if (index === -1) return false;
        this.tareas.splice(index, 1);
        return true;
    }
}



describe('ServicioTareas', () => {
    let servicio: servicioTareas;
    beforeEach(() => {
        servicio = new servicioTareas(new MockRepoLocal(), new MockClienteRemoto());
    });


    it('debería listar desde el origen correcto', async () => {
        const tareaLocal = await servicio.crear('local', { titulo: 'L', completada: false });
        const tareaRemoto = await servicio.crear('remoto', { titulo: 'R', completada: false });


        const locales = await servicio.listar('local');
        const remotas = await servicio.listar('remoto');


        expect(locales.map(t => t.titulo)).toContain('L');
        expect(remotas.map(t => t.titulo)).toContain('R');
    });


    it('debería sincronizar remoto a local', async () => {
        const clienteRemoto = servicio['clienteRemoto'];
        await clienteRemoto.crear({ titulo: 'Sync', completada: false });


        await servicio.sincronizarRemotoALocal();
        const locales = await servicio.listar('local');
        expect(locales.some(t => t.titulo === 'Sync')).toBe(true);
    });
});