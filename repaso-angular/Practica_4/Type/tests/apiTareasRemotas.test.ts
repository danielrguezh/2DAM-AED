import { tareasRemotas } from '../src/apiTareasRemota';
import type { Tarea } from '../src/models';


// Mock global fetch
(global as any).fetch = jest.fn();


describe('apiTareasRemota', () => {
    beforeEach(() => {
        (fetch as jest.Mock).mockClear();
    });


    it('obtenerTodas debería llamar fetch y devolver tareas', async () => {
        const mockTareas: Tarea[] = [{ id: 1, titulo: 'Mock', completada: false }];
        (fetch as jest.Mock).mockResolvedValue({ ok: true, json: async () => mockTareas });


        const tareas = await tareasRemotas.obtenerTodas();
        expect(fetch).toHaveBeenCalled();
        expect(tareas).toEqual(mockTareas);
    });


    it('crear debería llamar fetch con POST', async () => {
        const nueva: Omit<Tarea, 'id'> = { titulo: 'Nueva', completada: false };
        const creada: Tarea = { id: 1, ...nueva };
        (fetch as jest.Mock).mockResolvedValue({ ok: true, json: async () => creada });


        const result = await tareasRemotas.crear(nueva);
        expect(fetch).toHaveBeenCalledWith(expect.any(String), expect.objectContaining({ method: 'POST' }));
        expect(result).toEqual(creada);
    });
});