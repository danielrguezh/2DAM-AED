import { RepositorioTareasSqliteImpl } from '../src/repositorioTareasSqlite';
import { Tarea } from '../src/models';


describe('RepositorioTareasSqliteImpl', () => {
  let repo: RepositorioTareasSqliteImpl;


  beforeAll(() => {
    repo = new RepositorioTareasSqliteImpl();
  });


  afterAll(async () => {
    await repo.cerrarConexion();
  });


  it('debería inicializar la tabla', async () => {
    const tareas = await repo.obtenerTodas();
    expect(Array.isArray(tareas)).toBe(true);
  });


  it('debería crear, leer, actualizar y eliminar una tarea', async () => {
    const nueva: Omit<Tarea, 'id'> = { titulo: 'Test', completada: false };
    const creada = await repo.crear(nueva);
    expect(creada.id).toBeDefined();


    const obtenida = await repo.obtenerPorId(creada.id);
    expect(obtenida).not.toBeNull();
    expect(obtenida?.titulo).toBe('Test');


    const actualizada = await repo.actualizar(creada.id, { completada: true });
    expect(actualizada.id).toBe(creada.id);
    expect(actualizada.completada).toBe(true);



    const eliminada = await repo.eliminar(creada.id);
    expect(eliminada).toBe(true);


    const buscada = await repo.obtenerPorId(creada.id);
    expect(buscada).toBeNull();
  });
});