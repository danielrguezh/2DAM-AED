# 📚 Plan de Estudio: Gestor de Tareas Angular

## 🎯 Idea General del Proyecto

Este es un **Sistema de Gestión de Tareas (TODO App)** construido con **Angular 19** que se comunica con un backend API REST. Es una aplicación moderna que demuestra patrones de autenticación, autorización, manejo de tokens y CRUD de datos.

**Funcionalidades principales:**
- ✅ Autenticación con usuario y contraseña
- ✅ Generación y almacenamiento de tokens JWT
- ✅ Protección de rutas (solo usuarios autenticados pueden ver tareas)
- ✅ Crear, listar, actualizar y eliminar tareas
- ✅ Interfaz responsiva y user-friendly

---

## 🏗️ Arquitectura del Proyecto

```
Front (Angular App)
│
├── pages/              → Componentes de páginas (Login, Tareas, etc)
├── services/           → Lógica de negocio (auth, tasks)
├── guards/             → Protección de rutas
├── interceptors/       → Middleware HTTP (agregar tokens)
├── models/             → Interfaces TypeScript
└── shared/             → Componentes reutilizables (navbar)
```

### Stack Tecnológico
- **Angular 19** - Framework frontend
- **RxJS** - Manejo de observables y async
- **TypeScript** - Tipado fuerte
- **Reactive Forms** - Formularios reactivos
- **HttpClient** - Comunicación con backend
- **Angular Router** - Navegación entre páginas

---

## 🔄 Flujo de la Aplicación

### 1️⃣ Flujo de Login (Autenticación)

```
┌─────────────────────┐
│  Usuario ingresa    │
│ user + password     │
└──────────┬──────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ LoginComponent valida el formulario  │
│ (username, password requeridos)      │
└──────────┬─────────────────────────────┘
           │
           ▼
┌──────────────────────────────────────────────┐
│ AuthApiService.login() - POST al backend     │
│ Endpoint: http://localhost:8080/api/v1/      │
│           auth/login                         │
│ Body: { username: "user", password: "123" }  │
└──────────┬───────────────────────────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ Backend valida credenciales y       │
│ devuelve token JWT                   │
│ Response: { token: "jwt_aqui" }      │
└──────────┬──────────────────────────┘
           │
           ▼
┌──────────────────────────────────────────┐
│ El token se guarda en localStorage      │
│ localStorage.setItem('token', token)    │
└──────────┬───────────────────────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ Se redirige a /tareas                │
│ (AuthGuard verifica si hay token)    │
└──────────────────────────────────────┘
```

**Código del Login:**

```typescript
// services/auth-api.service.ts
@Injectable({ providedIn: 'root' })
export class AuthApiService {
    private readonly TOKEN_KEY = 'token';
    private baseUrl = 'http://localhost:8080/api/v1/auth/login';
    
    login(user: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(this.baseUrl, user).pipe(
            tap(res => {
                // Guardar token en el navegador
                localStorage.setItem(this.TOKEN_KEY, res.token);
            })
        );
    }
    
    getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
    }
    
    isLoggedIn(): boolean {
        return this.getToken() !== null;  // ¿Hay token?
    }
}
```

---

### 2️⃣ Flujo de Manejo del Token (Interceptor)

Una vez que el usuario está autenticado, **todos los requests HTTP necesitan incluir el token en el header**.

```
┌─────────────────────────────┐
│ Usuario hace request HTTP   │
│ GET /api/v1/tasks           │
└──────────┬──────────────────┘
           │
           ▼
┌────────────────────────────────────────────────┐
│ AuthInterceptor intercepta el request          │
│ (Se ejecuta ANTES de enviar al backend)        │
└──────────┬─────────────────────────────────────┘
           │
           ▼
┌────────────────────────────────────────────────┐
│ Obtiene el token del localStorage              │
│ token = localStorage.getItem('token')          │
└──────────┬─────────────────────────────────────┘
           │
           ▼
┌────────────────────────────────────────────────┐
│ Agrega el token al Header Authorization        │
│ Header: Authorization: Bearer jwt_aqui         │
└──────────┬─────────────────────────────────────┘
           │
           ▼
┌────────────────────────────────────────────────┐
│ El request se envía con el token               │
│ Backend valida el token                        │
└──────────┬─────────────────────────────────────┘
           │
           ├─ Token válido ──▶ Procesa request
           └─ Token inválido ▶ Retorna 401 (Unauthorized)
                                │
                                ▼
                    ┌─────────────────────────────┐
                    │ Logout (borra token)        │
                    │ Redirige a /login           │
                    └─────────────────────────────┘
```

**Código del Interceptor:**

```typescript
// interceptors/auth.interceptor.ts
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private authService: AuthApiService, private router: Router) {}

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        // 1. Obtener token
        const token = this.authService.getToken();

        // 2. Si hay token, clonamos el request y agregamos el header
        if (token) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            });
        }

        // 3. Enviar el request modificado
        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => {
                // 4. Si recibimos 401, logout
                if (error.status === 401) {
                    this.authService.logout();
                    this.router.navigate(['/login']);
                }
                return throwError(() => error);
            })
        );
    }
}
```

---

### 3️⃣ Flujo de Protección de Rutas (Guards)

Las rutas están protegidas con **Guards** que verifican si el usuario está autenticado.

```
┌──────────────────────────┐
│ Usuario accede a /tareas │
└──────────┬───────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ AuthGuard se ejecuta                 │
│ canActivate(): boolean               │
└──────────┬─────────────────────────────┘
           │
           ├─ isLoggedIn() = true  ──▶ ✅ Acceso permitido
           │
           └─ isLoggedIn() = false ──▶ 🚫 Redirige a /login
```

**Código del Guard:**

```typescript
// guards/auth.guard.ts
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(private authApiService: AuthApiService, private router: Router) {}

    canActivate(): boolean {
        if (this.authApiService.isLoggedIn()) {
            return true;  // Permitir acceso
        } else {
            this.router.navigate(['/login']);
            return false; // Bloquear acceso
        }
    }
}
```

**Rutas protegidas:**

```typescript
// app.routes.ts
export const routes: Routes = [
    // Público (solo para no autenticados)
    { path: 'login', component: LoginComponent, canActivate: [PublicGuard] },
    
    // Privado (solo para autenticados)
    { path: 'tareas', component: TasksComponent, canActivate: [AuthGuard] },
    { path: 'tareas/nueva', component: TaskNewComponent, canActivate: [AuthGuard] },
    
    // Otras rutas
    { path: 'acercaDe', component: AboutComponent },
    { path: 'home', component: HomeComponent },
    
    // Redirección inteligente
    { path: '', component: RedirectComponent },
];
```

---

### 4️⃣ Flujo de CRUD de Tareas

```
┌─────────────────────────────────────────────┐
│ Usuario en página /tareas                   │
└──────────┬──────────────────────────────────┘
           │
           ▼
    ┌──────────────────────────────────────┐
    │ 1️⃣ LISTAR TAREAS                     │
    │ GET /api/v1/tasks                    │
    │ + Header: Authorization: Bearer ...  │
    │ Response: [                          │
    │   {                                  │
    │     id: 1,                           │
    │     title: "Tarea 1",                │
    │     description: "Desc",             │
    │     completed: false                 │
    │   }                                  │
    │ ]                                    │
    └──────────┬───────────────────────────┘
               │ (Se transforma a español)
               ▼
    ┌──────────────────────────────────────┐
    │ Frontend maneja con interface Task:  │
    │ {                                    │
    │   id: 1,                             │
    │   titulo: "Tarea 1",                 │
    │   descripcion: "Desc",               │
    │   completada: false                  │
    │ }                                    │
    └──────────────────────────────────────┘
               │
               ├─ Usuario hace click en checkbox
               │
               ▼
    ┌──────────────────────────────────────┐
    │ 2️⃣ ACTUALIZAR TAREA                  │
    │ PATCH /api/v1/tasks/1                │
    │ Body: {                              │
    │   title: "Tarea 1",                  │
    │   completed: true                    │
    │ }                                    │
    └──────────────────────────────────────┘
               │
               ├─ Usuario hace click en eliminar
               │
               ▼
    ┌──────────────────────────────────────┐
    │ 3️⃣ ELIMINAR TAREA                    │
    │ DELETE /api/v1/tasks/1               │
    └──────────────────────────────────────┘
               │
               ├─ Usuario va a nueva tarea
               │
               ▼
    ┌──────────────────────────────────────┐
    │ 4️⃣ CREAR TAREA                       │
    │ POST /api/v1/tasks                   │
    │ Body: {                              │
    │   title: "Nueva tarea",              │
    │   description: "Descripción",        │
    │   completed: false                   │
    │ }                                    │
    │ Response: { id: 2, ... }             │
    └──────────────────────────────────────┘
```

**Código del Servicio de Tareas:**

```typescript
// services/tasks-api.service.ts
@Injectable({ providedIn: 'root' })
export class TasksApiService {
    private baseUrl = 'http://localhost:8080/api/v1/tasks';

    // Transforma datos del backend (inglés) a frontend (español)
    private fromBackend(backendTask: TaskBackend): Task {
        return {
            id: backendTask.id,
            titulo: backendTask.title,
            descripcion: backendTask.description,
            completada: backendTask.completed
        };
    }

    // Transforma datos del frontend (español) a backend (inglés)
    private toBackend(task: Task | NewTask): NewTaskBackend {
        return {
            title: task.titulo,
            description: task.descripcion,
            completed: task.completada
        };
    }

    // LISTAR tareas
    list(): Observable<Task[]> {
        return this.http.get<TaskBackend[]>(this.baseUrl).pipe(
            map(tasks => tasks.map(t => this.fromBackend(t)))
        );
    }

    // CREAR tarea
    create(data: NewTask): Observable<Task> {
        return this.http.post<TaskBackend>(this.baseUrl, this.toBackend(data)).pipe(
            map(t => this.fromBackend(t))
        );
    }

    // ACTUALIZAR tarea
    update(task: Task): Observable<Task> {
        return this.http.patch<TaskBackend>(
            `${this.baseUrl}/${task.id}`,
            this.toBackend(task)
        ).pipe(
            map(t => this.fromBackend(t))
        );
    }

    // ELIMINAR tarea
    remove(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}
```

---

## 📊 Transformación de Datos (Backend ↔ Frontend)

El proyecto tiene un patrón importante: **El backend usa inglés, el frontend usa español**.

```
Backend (API)              Frontend (Angular)
─────────────────          ──────────────────
title                  ←→  titulo
description            ←→  descripcion
completed              ←→  completada
```

**¿Por qué?** Permite que el backend sea agnóstico del idioma del cliente.

```typescript
// En tasks-api.service.ts
private fromBackend(backendTask: TaskBackend): Task {
    // Backend: { title, description, completed }
    // Frontend: { titulo, descripcion, completada }
    return {
        id: backendTask.id,
        titulo: backendTask.title,           // inglés → español
        descripcion: backendTask.description,
        completada: backendTask.completed
    };
}

private toBackend(task: Task): NewTaskBackend {
    // Frontend: { titulo, descripcion, completada }
    // Backend: { title, description, completed }
    return {
        title: task.titulo,                  // español → inglés
        description: task.descripcion,
        completed: task.completada
    };
}
```

---

## 📁 Modelos de Datos

### AuthModel
```typescript
export interface LoginRequest {
    username: string;   // Usuario
    password: string;   // Contraseña
}

export interface LoginResponse {
    token: string;      // JWT del backend
}
```

### TaskModel
```typescript
export interface Task {
    id: number;
    titulo: string;
    descripcion?: string;  // Opcional
    completada: boolean;
}

export type NewTask = Omit<Task, 'id'>;  // Para crear (sin ID)
```

---

## 🔐 Manejo del Token - Paso a Paso

### ¿Dónde se guarda?
```javascript
// localStorage (en el navegador)
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### ¿Cómo se obtiene?
```typescript
const token = localStorage.getItem('token');
// "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### ¿Cómo se usa en requests?
```typescript
// Sin interceptor (manual):
const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
});
this.http.get(url, { headers });

// Con interceptor (automático):
// El interceptor lo hace por nosotros en CADA request
```

### ¿Cuándo se elimina?
```typescript
// En logout (usuario cierra sesión o token expira)
logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
}
```

---

## 🚀 Ejemplo Completo: Flujo de Login a Tareas

### Paso 1: Usuario ingresa credenciales
```typescript
// pages/login/login.component.ts
login() {
    const data = { 
        username: 'juan', 
        password: '123456' 
    };
    
    this.auth.login(data).subscribe({
        next: () => {
            // ✅ Token guardado automáticamente
            this.router.navigateByUrl('/tareas');
        },
        error: err => {
            this.error = 'Credenciales inválidas';
        }
    });
}
```

### Paso 2: AuthApiService guarda el token
```typescript
// services/auth-api.service.ts
login(user: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.baseUrl, user).pipe(
        tap(res => {
            // Response del backend: { token: "jwt..." }
            localStorage.setItem('token', res.token);  // 💾 ¡Guardado!
        })
    );
}
```

### Paso 3: Usuario accede a /tareas (AuthGuard valida)
```typescript
// guards/auth.guard.ts
canActivate(): boolean {
    if (this.authApiService.isLoggedIn()) {
        // Token existe → acceso permitido
        return true;
    } else {
        this.router.navigate(['/login']);
        return false;
    }
}
```

### Paso 4: Componente de tareas carga la lista
```typescript
// pages/tasks/tasks.component.ts
ngOnInit(): void {
    this.tasksService.list().subscribe({
        next: (tasks) => {
            this.tasks = tasks;
        }
    });
}
```

### Paso 5: Interceptor agrega el token automáticamente
```typescript
// interceptors/auth.interceptor.ts
intercept(request: HttpRequest, next: HttpHandler) {
    const token = this.authService.getToken();  // Lee del localStorage
    
    if (token) {
        // Modifica el request
        request = request.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`  // ← Token en header
            }
        });
    }
    
    return next.handle(request);  // Envía el request modificado
}
```

### Paso 6: Backend procesa con el token
```
GET http://localhost:8080/api/v1/tasks
Header: Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

Backend valida el token:
- ✅ Token válido y no expirado → Devuelve lista de tareas
- ❌ Token inválido o expirado → Devuelve 401 (Unauthorized)
```

---

## 🔄 Casos de Uso Comunes

### ✅ Usuario se loguea correctamente
1. Ingresa usuario: "juan", contraseña: "123456"
2. Backend valida y devuelve token
3. Token se guarda en localStorage
4. Se redirige a /tareas
5. AuthGuard permite acceso

### ❌ Usuario intenta acceder sin token
1. Usuario accede a http://localhost:4200/tareas
2. AuthGuard verifica isLoggedIn()
3. No hay token en localStorage → false
4. Se redirige a /login automáticamente

### ❌ Token expira mientras el usuario está usando la app
1. Usuario hace un request (GET /tareas)
2. Backend rechaza con 401
3. Interceptor detecta el 401
4. Logout automático
5. Se redirige a /login
6. Usuario debe iniciar sesión nuevamente

### ✅ Usuario crea una nueva tarea
1. Usuario está en /tareas/nueva
2. Completa el formulario
3. Hace click en "Guardar"
4. TasksApiService.create() envía POST
5. Interceptor agrega el token
6. Backend crea la tarea y devuelve { id, titulo, ... }
7. Se transforma a modelo frontend
8. Se redirige a /tareas (mostrando la nueva tarea)

---

# 🔄 Cómo Adaptar el Proyecto: Cambiar "Tareas" por Otra Cosa

Supongamos que en lugar de **Tareas** queremos un gestor de **Películas**.

## Paso 1: Cambiar los Modelos

**Antes (Task):**
```typescript
export interface Task {
    id: number;
    titulo: string;
    descripcion?: string;
    completada: boolean;
}
```

**Después (Movie):**
```typescript
export interface Movie {
    id: number;
    titulo: string;
    duracion: number;      // en minutos
    director: string;
    anio: number;
    visto: boolean;        // visto o no visto
}

export type NewMovie = Omit<Movie, 'id'>;
```

---

## Paso 2: Cambiar las Rutas

**Antes:**
```typescript
{ path: 'tareas', component: TasksComponent, canActivate: [AuthGuard] },
{ path: 'tareas/nueva', component: TaskNewComponent, canActivate: [AuthGuard] },
```

**Después:**
```typescript
{ path: 'peliculas', component: MoviesComponent, canActivate: [AuthGuard] },
{ path: 'peliculas/nueva', component: MovieNewComponent, canActivate: [AuthGuard] },
```

---

## Paso 3: Cambiar el Servicio API

**Antes (TasksApiService):**
```typescript
@Injectable({ providedIn: 'root' })
export class TasksApiService {
    private baseUrl = 'http://localhost:8080/api/v1/tasks';
    
    list(): Observable<Task[]> { ... }
    create(data: NewTask): Observable<Task> { ... }
    update(task: Task): Observable<Task> { ... }
    remove(id: number): Observable<void> { ... }
}
```

**Después (MoviesApiService):**
```typescript
@Injectable({ providedIn: 'root' })
export class MoviesApiService {
    private baseUrl = 'http://localhost:8080/api/v1/movies';  // ← cambiar
    
    list(): Observable<Movie[]> {
        return this.http.get<MovieBackend[]>(this.baseUrl).pipe(
            map(movies => movies.map(m => this.fromBackend(m)))
        );
    }
    
    create(data: NewMovie): Observable<Movie> {
        return this.http.post<MovieBackend>(this.baseUrl, this.toBackend(data)).pipe(
            map(m => this.fromBackend(m))
        );
    }
    
    update(movie: Movie): Observable<Movie> {
        return this.http.patch<MovieBackend>(
            `${this.baseUrl}/${movie.id}`,
            this.toBackend(movie)
        ).pipe(
            map(m => this.fromBackend(m))
        );
    }
    
    remove(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
    
    // Transformaciones (backend inglés → frontend español)
    private fromBackend(backendMovie: MovieBackend): Movie {
        return {
            id: backendMovie.id,
            titulo: backendMovie.title,
            duracion: backendMovie.duration,
            director: backendMovie.director,
            anio: backendMovie.year,
            visto: backendMovie.watched
        };
    }
    
    private toBackend(movie: Movie | NewMovie): any {
        return {
            title: movie.titulo,
            duration: movie.duracion,
            director: movie.director,
            year: movie.anio,
            watched: movie.visto
        };
    }
}
```

---

## Paso 4: Cambiar los Componentes

**Componente de Lista (Movies):**
```typescript
// pages/movies/movies.component.ts
import { Component } from '@angular/core';
import { Movie } from '../../models/movie.model';
import { MoviesApiService } from '../../services/movies-api.service';

@Component({
    selector: 'app-movies',
    standalone: true,
    templateUrl: './movies.component.html',
    styleUrl: './movies.component.css',
})
export class MoviesComponent {
    movies: Movie[] = [];
    loading = true;
    error: string | null = null;

    constructor(public moviesService: MoviesApiService) {}

    ngOnInit(): void {
        this.moviesService.list().subscribe({
            next: (data) => {
                this.movies = data;
                this.loading = false;
            },
            error: () => {
                this.error = "No se pudieron cargar las películas";
                this.loading = false;
            },
        });
    }

    toggleWatched(movie: Movie) {
        const updatedMovie = { ...movie, visto: !movie.visto };
        this.moviesService.update(updatedMovie).subscribe({
            next: () => {
                this.movies = this.movies.map(m => 
                    m.id === movie.id ? updatedMovie : m
                );
            },
            error: () => this.error = 'No se pudo actualizar la película',
        });
    }

    remove(id: number) {
        this.moviesService.remove(id).subscribe({
            next: () => this.movies = this.movies.filter(m => m.id !== id),
            error: () => this.error = 'No se pudo eliminar la película',
        });
    }
}
```

**Componente de Nueva Película:**
```typescript
// pages/movie-new/movie-new.component.ts
@Component({
    selector: 'app-movie-new',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './movie-new.component.html',
    styleUrl: './movie-new.component.css',
})
export class MovieNewComponent {
    private fb = inject(FormBuilder);
    private movies = inject(MoviesApiService);
    private router = inject(Router);

    form = this.fb.nonNullable.group({
        titulo: this.fb.nonNullable.control('', [Validators.required]),
        duracion: this.fb.nonNullable.control(0, [Validators.required]),
        director: this.fb.nonNullable.control('', [Validators.required]),
        anio: this.fb.nonNullable.control(new Date().getFullYear()),
        visto: this.fb.nonNullable.control(false),
    });

    save() {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
            return;
        }
    
        const data = this.form.getRawValue();
        this.movies.create(data).subscribe({
            next: () => this.router.navigateByUrl('/peliculas'),
            error: err => console.error(err)
        });
    }
}
```

---

## Paso 5: Cambiar el HTML

**Antes (tasks.component.html):**
```html
<div *ngFor="let task of tasks" class="task-card">
    <input type="checkbox" 
           [checked]="task.completada" 
           (change)="completed(task)">
    <h3>{{ task.titulo }}</h3>
    <p>{{ task.descripcion }}</p>
    <button (click)="remove(task.id)">Eliminar</button>
</div>
```

**Después (movies.component.html):**
```html
<div *ngFor="let movie of movies" class="movie-card">
    <h3>{{ movie.titulo }} ({{ movie.anio }})</h3>
    <p><strong>Director:</strong> {{ movie.director }}</p>
    <p><strong>Duración:</strong> {{ movie.duracion }} min</p>
    
    <label>
        <input type="checkbox" 
               [checked]="movie.visto" 
               (change)="toggleWatched(movie)">
        Visto
    </label>
    
    <button (click)="remove(movie.id)">Eliminar</button>
</div>
```

---

## 📝 Resumen: Cambios Necesarios

| Elemento | Tareas | Películas |
|----------|--------|-----------|
| **Modelo** | `Task` | `Movie` |
| **Servicio** | `TasksApiService` | `MoviesApiService` |
| **Componentes** | `TasksComponent`, `TaskNewComponent` | `MoviesComponent`, `MovieNewComponent` |
| **Rutas** | `/tareas`, `/tareas/nueva` | `/peliculas`, `/peliculas/nueva` |
| **Endpoint** | `/api/v1/tasks` | `/api/v1/movies` |
| **Propiedades** | titulo, descripcion, completada | titulo, duracion, director, anio, visto |
| **Métodos API** | list, create, update, remove | list, create, update, remove |

---

## 🎯 Patrón General Aplicable

Este proyecto sigue un patrón que se puede aplicar a **cualquier recurso CRUD**:

1. **Definir el modelo** (interface)
2. **Crear el servicio API** (CRUD operations)
3. **Crear los componentes** (lista, detalle, crear, editar)
4. **Definir las rutas** (navegación)
5. **Agregar los guards** (autenticación/autorización)
6. **Usar el interceptor** (tokens automáticos)

**Otros ejemplos posibles:**
- 📚 Biblioteca de libros
- 🛍️ Tienda de productos
- 👥 Sistema de usuarios
- 📱 App de contactos
- 🎬 Galería de imágenes

Todos seguirían la misma estructura y lógica.

---

## 🔗 Referencias de Archivos Clave

- **Autenticación**: src/app/services/auth-api.service.ts
- **Token Automático**: src/app/interceptors/auth.interceptor.ts
- **Protección de Rutas**: src/app/guards/auth.guard.ts
- **Transformación de Datos**: src/app/services/tasks-api.service.ts
- **Rutas de la App**: src/app/app.routes.ts

---

## 💡 Conceptos Clave Resumidos

✅ **Backend**: API REST que valida tokens y devuelve datos  
✅ **Frontend**: Angular que consume la API  
✅ **Token JWT**: String que identifica al usuario autenticado  
✅ **localStorage**: Donde se guarda el token en el navegador  
✅ **Interceptor**: Middleware que agrega el token a cada request  
✅ **Guards**: Protegen rutas verificando autenticación  
✅ **Observables**: RxJS para manejar requests async  
✅ **Transformación**: Convertir datos entre formatos (inglés ↔ español)

---

# 📚 Resumen: Qué Hace Cada Clase

## 🔐 Servicios de Autenticación

### `AuthApiService` (services/auth-api.service.ts)
**Responsabilidad:** Gestionar la autenticación del usuario
- `login(user)` → Envía credenciales al backend y guarda el token
- `getToken()` → Obtiene el token guardado en localStorage
- `setToken(token)` → Guarda un token manualmente
- `logout()` → Elimina el token y cierra sesión
- `isLoggedIn()` → Verifica si hay token (usuario autenticado)

**Código clave:**
```typescript
login(user: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.baseUrl, user).pipe(
        tap(res => localStorage.setItem('token', res.token))
    );
}

isLoggedIn(): boolean {
    return this.getToken() !== null;
}
```

---

## 📋 Servicios de Datos

### `TasksApiService` (services/tasks-api.service.ts)
**Responsabilidad:** Comunicar con el backend para operaciones CRUD de tareas
- `list()` → Obtiene todas las tareas del usuario
- `get(id)` → Obtiene una tarea específica
- `create(data)` → Crea una nueva tarea
- `update(task)` → Actualiza una tarea existente
- `remove(id)` → Elimina una tarea
- `fromBackend()` → Transforma datos del backend (inglés → español)
- `toBackend()` → Transforma datos al formato del backend (español → inglés)

**Código clave:**
```typescript
list(): Observable<Task[]> {
    return this.http.get<TaskBackend[]>(this.baseUrl).pipe(
        map(tasks => tasks.map(t => this.fromBackend(t)))
    );
}

create(data: NewTask): Observable<Task> {
    return this.http.post<TaskBackend>(
        this.baseUrl, 
        this.toBackend(data)
    ).pipe(
        map(t => this.fromBackend(t))
    );
}
```

---

## 🛡️ Guards (Protección de Rutas)

### `AuthGuard` (guards/auth.guard.ts)
**Responsabilidad:** Proteger rutas que requieren autenticación
- Verifica si el usuario está logueado antes de permitir acceso
- Si no está autenticado, redirige a /login
- Se usa en rutas privadas como `/tareas` y `/tareas/nueva`

**Código clave:**
```typescript
canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
        return true;  // Permitir acceso
    } else {
        this.router.navigate(['/login']);
        return false; // Bloquear acceso
    }
}
```

---

### `PublicGuard` (guards/public.guard.ts)
**Responsabilidad:** Proteger rutas públicas que no deben ver usuarios autenticados
- Verifica si el usuario está logueado
- Si está autenticado y trata de ir a `/login`, lo redirige a `/tareas`
- Permite acceso a usuarios NO autenticados

**Código clave:**
```typescript
canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
        this.router.navigate(['/tareas']);
        return false;  // Bloquear acceso
    }
    return true;  // Permitir acceso a no autenticados
}
```

---

## 🔄 Interceptores

### `AuthInterceptor` (interceptors/auth.interceptor.ts)
**Responsabilidad:** Agregar el token JWT a TODOS los requests HTTP automáticamente
- Intercepta cada request antes de enviar
- Obtiene el token del localStorage
- Agrega el header `Authorization: Bearer {token}`
- Si backend devuelve 401, hace logout automático

**Código clave:**
```typescript
intercept(request: HttpRequest, next: HttpHandler): Observable<HttpEvent> {
    const token = this.authService.getToken();

    if (token) {
        request = request.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    }

    return next.handle(request).pipe(
        catchError((error: HttpErrorResponse) => {
            if (error.status === 401) {
                this.authService.logout();
                this.router.navigate(['/login']);
            }
            return throwError(() => error);
        })
    );
}
```

---

## 📄 Componentes de Páginas

### `LoginComponent` (pages/login/login.component.ts)
**Responsabilidad:** Formulario de login del usuario
- Valida que usuario y contraseña no estén vacíos
- Envía credenciales al servicio de autenticación
- Maneja errores de login (credenciales incorrectas)
- Redirige a `/tareas` si el login es exitoso

**Métodos:**
- `login()` → Valida y envía el formulario
- `cancel()` → Cancela y navega a tareas

---

### `TasksComponent` (pages/tasks/tasks.component.ts)
**Responsabilidad:** Mostrar lista de tareas del usuario
- Carga todas las tareas al inicializar
- Muestra estado de carga mientras obtiene datos
- Muestra errores si algo falla
- Permite marcar tareas como completadas
- Permite eliminar tareas
- Actualiza la UI automáticamente después de cambios

**Métodos:**
- `ngOnInit()` → Carga la lista de tareas
- `completed(task)` → Marca/desmarca tarea como completada
- `remove(id)` → Elimina una tarea

**Estados:**
- `tasks: Task[]` → Array de tareas
- `loading: boolean` → Está cargando datos
- `error: string | null` → Mensaje de error si hay

---

### `TaskNewComponent` (pages/task-new/task-new.component.ts)
**Responsabilidad:** Formulario para crear una nueva tarea
- Valida que el título tenga al menos 3 caracteres
- Valida que la descripción tenga al menos 5 caracteres (si se proporciona)
- Envía la tarea al backend
- Redirige a `/tareas` cuando se crea exitosamente

**Métodos:**
- `save()` → Valida y crea la tarea
- `cancel()` → Cancela y vuelve a tareas

**Validadores:**
- `titulo`: Requerido, minLength(3)
- `descripcion`: minLength(5)
- `completada`: Por defecto false

---

### `RedirectComponent` (pages/redirect/redirect.component.ts)
**Responsabilidad:** Componente inteligente de redirección
- Se usa en la ruta raíz `/`
- Si usuario está autenticado → redirige a `/tareas`
- Si usuario NO está autenticado → redirige a `/login`
- No muestra nada en pantalla (template vacío)

**Código clave:**
```typescript
ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
        this.router.navigate(['/tareas']);
    } else {
        this.router.navigate(['/login']);
    }
}
```

---

### `AboutComponent` (pages/about/about.component.ts)
**Responsabilidad:** Página de información general
- Página pública (sin autenticación requerida)
- Muestra información sobre la aplicación

---

### `HomeComponent` (pages/home/home.component.ts)
**Responsabilidad:** Página principal pública
- Página pública (sin autenticación requerida)
- Bienvenida y descripción de la app

---

## 📊 Modelos de Datos

### `auth.model.ts`
```typescript
export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    token: string;
}
```
Define la estructura de datos para login

---

### `task.model.ts`
```typescript
export interface Task {
    id: number;
    titulo: string;
    descripcion?: string;
    completada: boolean;
}

export type NewTask = Omit<Task, 'id'>;
```
Define la estructura de una tarea
- `Task`: Tarea completa con ID (del backend)
- `NewTask`: Tarea sin ID (para crear)

---

## 🧩 Componente Compartido

### `NavbarComponent` (shared/navbar/navbar.component.ts)
**Responsabilidad:** Barra de navegación global
- Muestra enlaces a las distintas páginas
- Botón de logout
- Se reutiliza en toda la aplicación

---

## 🔗 Archivo de Configuración Principal

### `app.routes.ts`
**Responsabilidad:** Definir todas las rutas de la aplicación
```typescript
export const routes: Routes = [
    // Público
    { path: 'login', component: LoginComponent, canActivate: [PublicGuard] },
    
    // Privado
    { path: 'tareas', component: TasksComponent, canActivate: [AuthGuard] },
    { path: 'tareas/nueva', component: TaskNewComponent, canActivate: [AuthGuard] },
    
    // Público
    { path: 'acercaDe', component: AboutComponent },
    { path: 'home', component: HomeComponent },
    
    // Redirección
    { path: '', component: RedirectComponent },
    { path: '**', redirectTo: '' }
];
```

---

### `app.config.ts`
**Responsabilidad:** Configuración principal de la aplicación
- Activa detección de cambios
- Registra HttpClient
- Registra el interceptor de autenticación
- Configura las rutas

---

## 📊 Diagrama de Dependencias

```
┌──────────────────────────────────────────────────┐
│           app.config.ts (Configuración)          │
│    - Registra servicios e interceptores          │
└────────────┬─────────────────────────────────────┘
             │
             ▼
┌──────────────────────────────────────────────────┐
│          app.routes.ts (Rutas)                   │
│    - Define páginas y protección                 │
└────────────┬─────────────────────────────────────┘
             │
             ├──────────────────────────────┬──────────────────────┐
             ▼                              ▼                      ▼
   ┌──────────────────┐      ┌─────────────────────┐   ┌──────────────────┐
   │  LoginComponent  │      │  TasksComponent     │   │ TaskNewComponent │
   └────────┬─────────┘      └─────────┬───────────┘   └────────┬─────────┘
            │                          │                        │
            ▼                          ▼                        ▼
   ┌──────────────────┐      ┌─────────────────────┐   ┌──────────────────┐
   │  AuthApiService  │      │ TasksApiService     │   │ TasksApiService  │
   └────────┬─────────┘      └─────────┬───────────┘   └────────┬─────────┘
            │                          │                        │
            └──────────────┬───────────┴────────────────────────┘
                           │
                           ▼
                ┌─────────────────────────┐
                │  HttpClient             │
                │  (con AuthInterceptor)  │
                └────────────┬────────────┘
                             │
                             ▼
                  ┌─────────────────────┐
                  │   Backend API       │
                  │  (localhost:8080)   │
                  └─────────────────────┘
```

---

## 🎯 Flujo de Ejecución Típico

```
1. Usuario accede a http://localhost:4200/
   ↓
2. RedirectComponent revisa isLoggedIn()
   ├─ Si true → redirige a /tareas
   └─ Si false → redirige a /login
   ↓
3. Si no autenticado: LoginComponent
   - Usuario ingresa credenciales
   - AuthApiService.login() hace POST al backend
   - Backend devuelve { token: "..." }
   - Token se guarda en localStorage
   - Redirige a /tareas
   ↓
4. AuthGuard verifica isLoggedIn() = true
   ↓
5. TasksComponent carga
   - ngOnInit() llama a TasksApiService.list()
   - AuthInterceptor agrega token automáticamente
   - Backend responde con tareas
   - TasksApiService transforma datos (inglés → español)
   - Componente muestra las tareas
   ↓
6. Usuario interactúa
   - Marcar como completada → TasksApiService.update()
   - Eliminar → TasksApiService.remove()
   - Crear nueva → Navega a /tareas/nueva → TaskNewComponent
   - Interceptor agrega token en TODOS los requests
   ↓
7. Si token expira o es inválido
   - Backend devuelve 401
   - Interceptor detecta 401
   - Limpia localStorage y redirige a /login
```

---

**Última actualización:** Enero 2026
