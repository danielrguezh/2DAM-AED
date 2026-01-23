# Práctica 4: Doble Persistencia de Tareas

## Descripción del proyecto

Esta aplicación TypeScript implementa un sistema de gestión de tareas con **doble persistencia**: datos locales en SQLite3 y datos remotos a través de una API REST. El proyecto demuestra cómo sincronizar datos entre diferentes fuentes de persistencia y proporcionar flexibilidad en el acceso a la información.

## Estructura del proyecto

Type/
├── src/  
│ ├── models.ts  
│ ├── repositorioTareasSqlite.ts  
│ ├── apiTareasRemota.ts  
│ ├── servicioTareas.ts  
│ └── index.ts  
├── tests/  
│ ├── repositorioTareasSqlite.test.ts
│ ├── apiTareasRemotas.test.ts
│ └── servicioTareas.test.ts
├── dist/  
├── tareas.db  
├── jest.config.js  
├── tsconfig.json  
├── package.json  
└── README.md

## Instalación y ejecución

### Prerrequisitos

- Node.js (versión 16 o superior)
- npm o yarn
- API REST Java (ver sección "API REST")

### 1. Instalación de dependencias

```bash
cd Type/
npm install
```

### 2. Compilación y ejecución

**Desarrollo (con ts-node):**

```bash
npm run dev
```

**Producción:**

```bash
npm run build
npm start
```

### 4. Ejecución de tests

```bash
npm test
```

## Configuración de la API REST

La aplicación requiere una API REST Java (Spring Boot + H2) para la persistencia remota. El proyecto se encuentra en:

```
Rest/rest-ejercicio/
```

### Iniciar la API Java

```bash
cd Rest/rest-ejercicio/
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080/api/tareas`

### Endpoints disponibles

| Método | Endpoint           | Descripción             |
| ------ | ------------------ | ----------------------- |
| GET    | `/api/tareas`      | Listar todas las tareas |
| GET    | `/api/tareas/{id}` | Obtener tarea por ID    |
| POST   | `/api/tareas`      | Crear nueva tarea       |
| PUT    | `/api/tareas/{id}` | Actualizar tarea        |
| DELETE | `/api/tareas/{id}` | Eliminar tarea          |

## Arquitectura y diseño

## Sincronización de datos

El sistema implementa sincronización **remoto → local** con las siguientes estrategias:

### Estrategia incremental (implementada)

1. **Comparación por ID**: Se comparan las tareas por su ID único
2. **Actualización de existentes**: Se actualizan las tareas que ya existen localmente
3. **Inserción de nuevas**: Se añaden las tareas que no existen en local
4. **Manejo de conflictos**: La fuente remota tiene prioridad

### Uso de sincronización

```typescript
// Sincronizar datos remotos hacia local
await servicio.sincronizarRemotoALocal();

// Verificar estado después de sincronización
const tareasLocales = await servicio.listar("local");
console.log(
  `Tareas locales después de sincronización: ${tareasLocales.length}`
);
```

## Ejemplos de uso

### Flujo básico de trabajo

```typescript
import { ServicioTareas } from "./src/servicioTareas";

// Inicializar servicio
const servicio = new ServicioTareas(repositorioLocal, clienteRemoto);

// 1. Mostrar tareas remotas iniciales
const remotas = await servicio.listar("remoto");
console.log("Tareas remotas:", remotas);

// 2. Sincronizar remoto → local
await servicio.sincronizarRemotoALocal();

// 3. Mostrar tareas locales después de sincronización
const locales = await servicio.listar("local");
console.log("Tareas locales:", locales);

// 4. Crear nueva tarea en local
const nuevaTarea = await servicio.crear("local", {
  titulo: "Nueva tarea local",
  descripcion: "Tarea creada en SQLite",
  completada: false,
});

// 5. Crear nueva tarea en remoto
const tareaRemota = await servicio.crear("remoto", {
  titulo: "Nueva tarea remota",
  descripcion: "Tarea creada en API",
  completada: false,
});

// 6. Verificar ambas fuentes
const todasLocales = await servicio.listar("local");
const todasRemotas = await servicio.listar("remoto");
```

### Filtrado de tareas

```typescript
// Listar tareas pendientes
const pendientes = await servicio.listar("local", "pendientes");

// Listar tareas completadas
const completadas = await servicio.listar("remoto", "completadas");

// Listar todas las tareas
const todas = await servicio.listar("local", "todas");
```

### Operaciones CRUD

```typescript
// Actualizar tarea
const actualizada = await servicio.actualizar("local", 1, {
  titulo: "Título actualizado",
  completada: true,
});

// Eliminar tarea
const eliminada = await servicio.eliminar("remoto", 2);
```
