
import sqlite3 from 'sqlite3';
import { Tarea } from './models';

export interface RepositorioTareasSqlite {
    obtenerTodas(): Promise<Tarea[]>;
    obtenerPorId(id: number): Promise<Tarea | null>;
    crear(tarea: Omit<Tarea, 'id'>): Promise<Tarea>;
    actualizar(id: number, tarea: Partial<Tarea>): Promise<Tarea>;
    eliminar(id: number): Promise<boolean>;
}

export class RepositorioTareasSqliteImpl implements RepositorioTareasSqlite {

    private db: sqlite3.Database;
    private dbPath: string = 'tareas.db';

    constructor() {
        this.db = new sqlite3.Database(this.dbPath);
        this.inicializarTabla();
    }

    private inicializarTabla(): void {
        const sql = `
            CREATE TABLE IF NOT EXISTS tareas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descripcion TEXT,
                completada BOOLEAN DEFAULT false
            )
        `;
        this.db.run(sql, (err) => {
            if (err) {
                console.error('Error creando tabla:', err.message);
            } else {
                console.log('Tabla tareas inicializada correctamente');
            }
        });
    }

    obtenerTodas(): Promise<Tarea[]> {
        return new Promise((resolve, reject) => {
            const sql = 'SELECT * FROM tareas';
            this.db.all(sql, [], (err, rows: any[]) => {
                if (err) {
                    reject(err);
                } else {
                    const tareas: Tarea[] = rows.map(row => ({
                        id: row.id,
                        titulo: row.titulo,
                        descripcion: row.descripcion,
                        completada: Boolean(row.completada)
                    }));
                    resolve(tareas);
                }
            });
        });
    }

    obtenerPorId(id: number): Promise<Tarea | null> {
        return new Promise((resolve, reject) => {
            const sql = 'SELECT * FROM tareas WHERE id = ?';
            this.db.get(sql, [id], (err, row: any) => {
                if (err) {
                    reject(err);
                } else {
                    if (row) {
                        const tarea: Tarea = {
                            id: row.id,
                            titulo: row.titulo,
                            descripcion: row.descripcion,
                            completada: Boolean(row.completada)
                        };
                        resolve(tarea);
                    } else {
                        resolve(null);
                    }
                }
            });
        });
    }

    crear(tarea: Omit<Tarea, 'id'>): Promise<Tarea> {
        return new Promise((resolve, reject) => {
            const sql = 'INSERT INTO tareas (titulo, descripcion, completada) VALUES (?, ?, ?)';
            this.db.run(
                sql,
                [tarea.titulo, tarea.descripcion || null, tarea.completada ? 1 : 0],
                function (err) {
                    if (err) {
                        reject(err);
                    } else {
                        const nuevaTarea: Tarea = {
                            id: this.lastID,
                            titulo: tarea.titulo,
                            descripcion: tarea.descripcion,
                            completada: tarea.completada
                        };
                        resolve(nuevaTarea);
                    }
                }
            );
        });
    }

    actualizar(id: number, tarea: Partial<Tarea>): Promise<Tarea> {
        return new Promise((resolve, reject) => {
            const campos: string[] = [];
            const valores: any[] = [];

            if (tarea.titulo !== undefined) {
                campos.push('titulo = ?');
                valores.push(tarea.titulo);
            }

            if (tarea.descripcion !== undefined) {
                campos.push('descripcion = ?');
                valores.push(tarea.descripcion);
            }

            if (tarea.completada !== undefined) {
                campos.push('completada = ?');
                valores.push(tarea.completada ? 1 : 0);
            }

            if (campos.length === 0) {
                reject(new Error('No hay campos para actualizar'));
                return;
            }

            valores.push(id);

            const sqlUpdate = `UPDATE tareas SET ${campos.join(', ')} WHERE id = ?`;

            this.db.run(sqlUpdate, valores, (err) => {
                if (err) {
                    reject(err);
                    return;
                }

                const sqlSelect = 'SELECT * FROM tareas WHERE id = ?';
                this.db.get(sqlSelect, [id], (err, row: any) => {
                    if (err) {
                        reject(err);
                    } else if (!row) {
                        reject(new Error('Tarea no encontrada tras actualizar'));
                    } else {
                        const tareaActualizada: Tarea = {
                            id: row.id,
                            titulo: row.titulo,
                            descripcion: row.descripcion,
                            completada: Boolean(row.completada)
                        };
                        resolve(tareaActualizada);
                    }
                });
            });
        });
    }


    eliminar(id: number): Promise<boolean> {
        return new Promise((resolve, reject) => {
            const sql = 'DELETE FROM tareas WHERE id = ?';
            this.db.run(sql, [id], function (err) {
                if (err) {
                    reject(err);
                } else {
                    resolve(this.changes > 0);
                }
            });
        });
    }

    /**
     * Método para cerrar la conexión a la base de datos
     */
    cerrarConexion(): Promise<void> {
        return new Promise((resolve, reject) => {
            this.db.close((err) => {
                if (err) {
                    reject(err);
                } else {
                    console.log('Conexión a base de datos cerrada');
                    resolve();
                }
            });
        });
    }
}
