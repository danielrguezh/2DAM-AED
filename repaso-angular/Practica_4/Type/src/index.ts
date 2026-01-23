// src/index.ts

import { RepositorioTareasSqliteImpl } from './repositorioTareasSqlite';
import { tareasRemotas } from './apiTareasRemota';
import { servicioTareas, OrigenDatos } from './servicioTareas';

async function main() {
    const repositorioLocal = new RepositorioTareasSqliteImpl();
    const servicio = new servicioTareas(repositorioLocal, tareasRemotas);

    try {
        console.log('--- Tareas remotas ---');
        const tareasRemotas = await servicio.listar('remoto');
        console.log(tareasRemotas);

        console.log('\n--- Sincronizando remoto → local ---');
        await servicio.sincronizarRemotoALocal();

        console.log('\n--- Tareas locales tras sincronización ---');
        const tareasLocales = await servicio.listar('local');
        console.log(tareasLocales);

        console.log('\n--- Crear tarea en local ---');
        const tareaLocal = await servicio.crear('local', {
            titulo: 'Tarea local ejemplo',
            descripcion: 'Creada en SQLite',
            completada: false
        });
        console.log(tareaLocal);

        console.log('\n--- Crear tarea en remoto ---');
        const tareaRemota = await servicio.crear('remoto', {
            titulo: 'Tarea remota ejemplo',
            descripcion: 'Creada en API REST',
            completada: false
        });
        console.log(tareaRemota);

        console.log('\n--- Listar tareas locales y remotas finales ---');
        const finalLocales = await servicio.listar('local');
        const finalRemotas = await servicio.listar('remoto');
        console.log('Locales:', finalLocales);
        console.log('Remotas:', finalRemotas);

    } catch (error) {
        console.error('Error en el flujo de ejemplo:', error);
    } finally {
        await repositorioLocal.cerrarConexion();
        console.log('\nConexión SQLite cerrada');
    }
}

main();
