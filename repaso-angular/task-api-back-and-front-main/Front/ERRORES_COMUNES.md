# 🐛 Guía de Errores Comunes y Soluciones

## Tabla de Contenidos
1. [Errores de Autenticación](#errores-de-autenticación)
2. [Errores de Token](#errores-de-token)
3. [Errores de CORS](#errores-de-cors)
4. [Errores de Guards y Rutas](#errores-de-guards-y-rutas)
5. [Errores de Interceptor](#errores-de-interceptor)
6. [Errores de Observables](#errores-de-observables)
7. [Errores de Formularios](#errores-de-formularios)
8. [Errores de Componentes](#errores-de-componentes)
9. [Errores de Estado](#errores-de-estado)
10. [Errores de Transformación de Datos](#errores-de-transformación-de-datos)

---

# Errores de Autenticación

## ❌ Error 1: "No puedo loguearme - Backend siempre rechaza las credenciales"

### Síntomas
```
HTTP 401 Unauthorized
Error: Invalid credentials
```

### Posibles causas
1. Backend no está corriendo
2. URL del backend es incorrecta
3. Formato del body no coincide con lo que espera el backend
4. Credenciales inválidas

### Solución

**1. Verificar que el backend está corriendo:**
```powershell
# En terminal, verifica que el backend está en http://localhost:8080
curl http://localhost:8080/api/v1/auth/login -X POST -H "Content-Type: application/json" `
  -d '{"username":"admin","password":"123456"}'
```

**2. Verificar la URL en el servicio:**
```typescript
// services/auth-api.service.ts
@Injectable({ providedIn: 'root' })
export class AuthApiService {
    // ❌ MAL - Falta /api/v1/auth/login
    // private baseUrl = 'http://localhost:8080/login';
    
    // ✅ BIEN
    private baseUrl = 'http://localhost:8080/api/v1/auth/login';
    
    login(user: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(this.baseUrl, user).pipe(
            tap(res => {
                localStorage.setItem('token', res.token);
            })
        );
    }
}
```

**3. Verificar el formato del request:**
```typescript
// ❌ MAL - JSON keys incorrecto
{
  "user": "admin",      // ← Debe ser "username"
  "pwd": "123456"       // ← Debe ser "password"
}

// ✅ BIEN
{
  "username": "admin",
  "password": "123456"
}
```

**4. Verificar credenciales de prueba en el backend:**
```typescript
// Usa las credenciales que el backend tiene configurado
// Por defecto suele ser:
username: "admin"
password: "123456"

// O pregunta al equipo del backend cuáles son las credenciales válidas
```

---

## ❌ Error 2: "El componente de login no muestra errores cuando fallo"

### Síntomas
```
Usuario intenta login incorrecto pero no aparece mensaje de error
```

### Solución

**Asegúrate de que el componente maneja errores:**
```typescript
// pages/login/login.component.ts
export class LoginComponent {
    error: string | null = null;  // ← Necesario

    login() {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
            return;
        }
        
        this.error = null;  // ← Limpiar error anterior
        const data = this.form.getRawValue();
        
        this.auth.login(data).subscribe({
            next: () => {
                this.router.navigateByUrl('/tareas');
            },
            error: err => {
                // ← Capturar el error
                this.error = 'Usuario o contraseña incorrectos';
                console.error('Error de login:', err);
            }
        });
    }
}
```

**En el HTML, mostrar el error:**
```html
<!-- pages/login/login.component.html -->
<form [formGroup]="form" (ngSubmit)="login()">
    
    <!-- Mostrar error -->
    <div *ngIf="error" class="error-message">
        <p>{{ error }}</p>
    </div>
    
    <input type="text" formControlName="username" placeholder="Usuario">
    <input type="password" formControlName="password" placeholder="Contraseña">
    
    <button type="submit">Iniciar Sesión</button>
</form>
```

---

# Errores de Token

## ❌ Error 3: "El token no se guarda en localStorage"

### Síntomas
```
- Usuario se loguea pero cuando recarga la página pierde la sesión
- isLoggedIn() siempre devuelve false
```

### Solución

**Verificar que el interceptor está registrado:**
```typescript
// app.config.ts
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
    providers: [
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideHttpClient(
            // ❌ MAL - Falta registrar el interceptor
            // withInterceptors([])
            
            // ✅ BIEN
            withInterceptors([authInterceptor])  // ← Agregar aquí
        ),
        provideRouter(routes)
    ]
};
```

**O si usas la versión antigua, registra en el módulo:**
```typescript
// app.module.ts
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth.interceptor';

@NgModule({
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true  // ← Importante: multi: true
        }
    ]
})
export class AppModule { }
```

**Verificar que se guarda en login:**
```typescript
// services/auth-api.service.ts
login(user: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.baseUrl, user).pipe(
        tap(res => {
            // ❌ MAL - Verificar la respuesta
            console.log('Response:', res);
            
            // ✅ BIEN - Asegúrate que 'token' existe
            if (res && res.token) {
                localStorage.setItem('token', res.token);
                console.log('Token guardado:', res.token);
            } else {
                console.error('No token en response:', res);
            }
        })
    );
}
```

**Verificar en DevTools (Ctrl+Shift+I):**
```javascript
// En la consola del navegador
localStorage.getItem('token')
// Debería devolver el token, no null
```

---

## ❌ Error 4: "El token está guardado pero no se usa en los requests"

### Síntomas
```
- Token guardado en localStorage
- Pero backend responde 401 en GET /api/v1/tasks
- Headers no incluyen Authorization
```

### Solución

**Verificar el interceptor (función):**
```typescript
// interceptors/auth.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthApiService } from '../services/auth-api.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthApiService);
    const token = authService.getToken();
    
    // ✅ BIEN
    if (token) {
        req = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    }
    
    return next(req);
};
```

**O verificar el interceptor (clase):**
```typescript
// interceptors/auth.interceptor.ts
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private authService: AuthApiService) {}

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const token = this.authService.getToken();

        if (token) {
            // ✅ BIEN
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            });
        }

        return next.handle(request);
    }
}
```

**Verificar en DevTools (Network tab):**
```
1. Abre la pestaña Network (F12 → Network)
2. Haz un request (click en botón que llama a GET /api/v1/tasks)
3. Selecciona el request en la lista
4. Ve a Headers
5. Busca "Authorization" en los request headers
6. Debería ser: Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

---

## ❌ Error 5: "Token expirado pero la app no redirige a login"

### Síntomas
```
- Backend devuelve 401 cuando token expira
- Pero la app no detecta el error
- Usuario sigue viendo la página pero sin datos
```

### Solución

**El interceptor debe manejar 401:**
```typescript
// interceptors/auth.interceptor.ts
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(
        private authService: AuthApiService, 
        private router: Router
    ) {}

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
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
                // ✅ BIEN - Detectar 401
                if (error.status === 401) {
                    console.log('Token expirado o inválido');
                    this.authService.logout();  // Limpiar localStorage
                    this.router.navigate(['/login']);  // Redirigir a login
                }
                return throwError(() => error);
            })
        );
    }
}
```

---

# Errores de CORS

## ❌ Error 6: "Access-Control-Allow-Origin error"

### Síntomas
```
CORS policy: Access to XMLHttpRequest at 'http://localhost:8080/...'
from origin 'http://localhost:4200' has been blocked by CORS policy
```

### Solución

**Este error lo debe resolver el BACKEND, pero puedes verificar:**

```typescript
// En el backend (por ejemplo si usas Spring Boot):
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:4200")  // ← Angular
                    .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
```

**Desde el frontend, asegúrate de:**
```typescript
// app.config.ts
import { provideHttpClient, withXsrfConfiguration } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
    providers: [
        provideHttpClient(
            // ✅ BIEN - Si el backend requiere CSRF
            withXsrfConfiguration({
                cookieName: 'XSRF-TOKEN',
                headerName: 'X-XSRF-TOKEN'
            })
        )
    ]
};
```

**Workaround temporal (NO USAR EN PRODUCCIÓN):**
```
Si el backend no tiene CORS configurado, puedes:
1. Usar un proxy en desarrollo (ng serve --proxy-config proxy.conf.json)
2. Usar una extensión del navegador para bypass CORS (solo desarrollo)
3. Decirle al backend que configure CORS correctamente
```

---

# Errores de Guards y Rutas

## ❌ Error 7: "Usuario no autenticado puede acceder a /tareas"

### Síntomas
```
- Acceder a http://localhost:4200/tareas sin token
- Debería redirigir a /login pero no lo hace
- Usuario ve la página vacía
```

### Solución

**El guard no está configurado correctamente:**

```typescript
// ❌ MAL - AuthGuard no valida correctamente
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(private auth: AuthApiService, private router: Router) {}

    canActivate(): boolean {
        // ❌ MAL - Siempre devuelve true
        return true;
    }
}

// ✅ BIEN
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(private auth: AuthApiService, private router: Router) {}

    canActivate(): boolean {
        if (this.auth.isLoggedIn()) {
            return true;
        } else {
            this.router.navigate(['/login']);
            return false;
        }
    }
}
```

**Verificar que está en las rutas:**
```typescript
// app.routes.ts
export const routes: Routes = [
    // ✅ BIEN - Con guard
    { 
        path: 'tareas', 
        component: TasksComponent, 
        canActivate: [AuthGuard]  // ← IMPORTANTE
    },
    
    // ❌ MAL - Sin guard
    { 
        path: 'tareas', 
        component: TasksComponent
        // No tiene canActivate
    },
];
```

---

## ❌ Error 8: "Usuario autenticado puede acceder a /login"

### Síntomas
```
- Usuario logueado intenta ir a /login
- Debería redirigir a /tareas pero no lo hace
```

### Solución

**El PublicGuard debe estar en /login:**

```typescript
// guards/public.guard.ts
@Injectable({ providedIn: 'root' })
export class PublicGuard implements CanActivate {
    constructor(private auth: AuthApiService, private router: Router) {}

    canActivate(): boolean {
        if (this.auth.isLoggedIn()) {
            // ← Si está logueado, no puede ir a login
            this.router.navigate(['/tareas']);
            return false;
        }
        return true;  // ← Si NO está logueado, puede ir
    }
}

// app.routes.ts
export const routes: Routes = [
    { 
        path: 'login', 
        component: LoginComponent, 
        canActivate: [PublicGuard]  // ← IMPORTANTE
    },
];
```

---

## ❌ Error 9: "Ruta '' redirige al sitio incorrecto"

### Síntomas
```
- Usuario accede a http://localhost:4200/
- Debería ir a /login o /tareas según autenticación
- Pero va a sitio equivocado o se queda en blanco
```

### Solución

**El RedirectComponent debe estar bien:**

```typescript
// pages/redirect/redirect.component.ts
@Component({
    selector: 'app-redirect',
    standalone: true,
    template: '',  // ← Componente vacío
})
export class RedirectComponent implements OnInit {
    constructor(private auth: AuthApiService, private router: Router) {}

    ngOnInit(): void {
        if (this.auth.isLoggedIn()) {
            this.router.navigate(['/tareas']);  // ← Si tiene token, a tareas
        } else {
            this.router.navigate(['/login']);   // ← Si no, a login
        }
    }
}

// app.routes.ts
export const routes: Routes = [
    // ... otras rutas
    { path: '', component: RedirectComponent },  // ← Ruta raíz
    { path: '**', redirectTo: '' },              // ← Catch-all
];
```

---

# Errores de Interceptor

## ❌ Error 10: "El interceptor no está registrado"

### Síntomas
```
- Requests no incluyen Authorization header
- Error 401 en todos los requests a recursos protegidos
```

### Solución

**Angular 19 (versión nueva con standalone):**
```typescript
// app.config.ts
import { provideHttpClient, withInterceptors } from '@angular/common/http';

// Primero crea el interceptor como función:
export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthApiService);
    const token = authService.getToken();
    
    if (token) {
        req = req.clone({
            setHeaders: { Authorization: `Bearer ${token}` }
        });
    }
    
    return next(req);
};

// Luego regístralo en app.config.ts
export const appConfig: ApplicationConfig = {
    providers: [
        provideHttpClient(
            withInterceptors([authInterceptor])  // ← Aquí
        )
    ]
};
```

**Angular <19 (versión vieja con módulos):**
```typescript
// app.module.ts
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth.interceptor';

@NgModule({
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true  // ← IMPORTANTE
        }
    ]
})
export class AppModule { }
```

---

## ❌ Error 11: "El interceptor no maneja errores 401"

### Síntomas
```
- Token expira
- Backend devuelve 401
- App no limpia token ni redirige a login
```

### Solución

```typescript
// interceptors/auth.interceptor.ts
export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthApiService);
    const router = inject(Router);
    const token = authService.getToken();
    
    if (token) {
        req = req.clone({
            setHeaders: { Authorization: `Bearer ${token}` }
        });
    }
    
    // ✅ BIEN - Manejar errores
    return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
            if (error.status === 401) {
                authService.logout();
                router.navigate(['/login']);
            }
            return throwError(() => error);
        })
    );
};
```

---

# Errores de Observables

## ❌ Error 12: "Memory leak - suscripción no se desuscribe"

### Síntomas
```
- Angular emite advertencia en consola
- Posibles memory leaks en la aplicación
```

### Solución

**Opción 1: Desuscribirse manualmente**
```typescript
// ❌ MAL - Sin desuscribirse
export class TasksComponent implements OnInit {
    constructor(private tasksService: TasksApiService) {}
    
    ngOnInit() {
        this.tasksService.list().subscribe(tasks => {
            this.tasks = tasks;
        });
        // ← Nunca se desuscribe
    }
}

// ✅ BIEN - Con unsubscribe
export class TasksComponent implements OnInit, OnDestroy {
    private subscription: Subscription | null = null;
    
    constructor(private tasksService: TasksApiService) {}
    
    ngOnInit() {
        this.subscription = this.tasksService.list().subscribe(tasks => {
            this.tasks = tasks;
        });
    }
    
    ngOnDestroy() {
        this.subscription?.unsubscribe();
    }
}
```

**Opción 2: Usar takeUntil**
```typescript
export class TasksComponent implements OnInit, OnDestroy {
    private destroy$ = new Subject<void>();
    
    ngOnInit() {
        this.tasksService.list()
            .pipe(
                takeUntil(this.destroy$)  // ← Desuscribirse automáticamente
            )
            .subscribe(tasks => {
                this.tasks = tasks;
            });
    }
    
    ngOnDestroy() {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
```

**Opción 3: Usar async pipe en template (MEJOR)**
```typescript
// pages/tasks/tasks.component.ts
export class TasksComponent {
    tasks$ = this.tasksService.list();  // ← Observable, no suscripción
    
    constructor(private tasksService: TasksApiService) {}
}

// pages/tasks/tasks.component.html
<div *ngFor="let task of tasks$ | async">  <!-- ← async pipe desuscribe automáticamente -->
    {{ task.titulo }}
</div>
```

---

## ❌ Error 13: "Error no se propaga correctamente desde servicio a componente"

### Síntomas
```
- Servicio tiene error
- Pero el componente no lo ve
- El estado de error no se actualiza
```

### Solución

**Asegúrate de que el servicio propaga errores:**

```typescript
// ❌ MAL - El servicio traga el error
@Injectable({ providedIn: 'root' })
export class TasksApiService {
    list(): Observable<Task[]> {
        return this.http.get<Task[]>(this.baseUrl).pipe(
            // ❌ Esto traga el error
            catchError(() => {
                return of([]);  // ← Devuelve array vacío
            })
        );
    }
}

// ✅ BIEN - Propagar el error
@Injectable({ providedIn: 'root' })
export class TasksApiService {
    list(): Observable<Task[]> {
        return this.http.get<Task[]>(this.baseUrl);
        // ← Sin catchError, el error se propaga
    }
}

// En el componente, capturar el error
export class TasksComponent {
    error: string | null = null;
    
    ngOnInit() {
        this.tasksService.list().subscribe({
            next: (tasks) => {
                this.tasks = tasks;
            },
            error: (err) => {
                this.error = 'No se pudieron cargar las tareas';
                console.error(err);
            }
        });
    }
}
```

---

# Errores de Formularios

## ❌ Error 14: "Formulario enviado con datos inválidos"

### Síntomas
```
- Usuario hace click en enviar
- Formulario tiene campos vacíos
- Se envía de todas formas al backend
- Backend rechaza con error 400
```

### Solución

**Validar ANTES de enviar:**

```typescript
// ❌ MAL - Sin validar
export class TaskNewComponent {
    save() {
        const data = this.form.getRawValue();
        this.tasksService.create(data).subscribe(...);
    }
}

// ✅ BIEN - Validar primero
export class TaskNewComponent {
    save() {
        // 1. Validar formulario
        if (this.form.invalid) {
            // 2. Marcar como tocado para mostrar errores
            this.form.markAllAsTouched();
            return;  // 3. No enviar si es inválido
        }
        
        const data = this.form.getRawValue();
        this.tasksService.create(data).subscribe(...);
    }
}
```

**En el HTML, mostrar errores:**

```html
<form [formGroup]="form" (ngSubmit)="save()">
    
    <!-- Campo con validación -->
    <label>
        Título:
        <input type="text" formControlName="titulo">
        
        <!-- Mostrar error si es inválido y fue tocado -->
        <div *ngIf="form.get('titulo')?.invalid && form.get('titulo')?.touched" class="error">
            El título es requerido
        </div>
    </label>
    
    <button type="submit">Guardar</button>
</form>
```

---

## ❌ Error 15: "El formulario no tiene los validadores necesarios"

### Síntomas
```
- Usuario puede enviar título vacío
- Usuario puede enviar descripción de 1 carácter
- Backend rechaza con error
```

### Solución

```typescript
// ❌ MAL - Sin validadores
form = this.fb.nonNullable.group({
    titulo: this.fb.nonNullable.control(''),
    descripcion: this.fb.nonNullable.control(''),
});

// ✅ BIEN - Con validadores
form = this.fb.nonNullable.group({
    titulo: this.fb.nonNullable.control('', [
        Validators.required,           // ← Obligatorio
        Validators.minLength(3)        // ← Mínimo 3 caracteres
    ]),
    descripcion: this.fb.nonNullable.control('', [
        Validators.minLength(5)        // ← Mínimo 5 caracteres si se rellena
    ]),
    completada: this.fb.nonNullable.control(false)
});
```

---

# Errores de Componentes

## ❌ Error 16: "La página no muestra los datos - componente vacío"

### Síntomas
```
- Usuario se loguea
- Ve la página pero está vacía
- Console log muestra que task[] = []
- No hay errores en consola
```

### Solución

**Verificar que el ngOnInit está llamando al servicio:**

```typescript
// ❌ MAL - Falta cargar datos
export class TasksComponent {
    tasks: Task[] = [];
    
    constructor(public tasksService: TasksApiService) {}
    
    // ← Falta ngOnInit
}

// ✅ BIEN - Con carga de datos
export class TasksComponent {
    tasks: Task[] = [];
    loading = true;
    error: string | null = null;
    
    constructor(public tasksService: TasksApiService) {}
    
    ngOnInit(): void {  // ← IMPORTANTE
        this.tasksService.list().subscribe({
            next: (data) => {
                this.tasks = data;
                this.loading = false;
            },
            error: () => {
                this.error = "No se pudieron cargar las tareas";
                this.loading = false;
            }
        });
    }
}
```

**En el HTML, mostrar estados:**

```html
<div *ngIf="loading">Cargando...</div>

<div *ngIf="error" class="error">{{ error }}</div>

<div *ngIf="tasks.length === 0 && !loading">
    No hay tareas
</div>

<div *ngFor="let task of tasks">
    {{ task.titulo }}
</div>
```

---

## ❌ Error 17: "Al eliminar/editar, los datos no se actualizan en el UI"

### Síntomas
```
- Usuario elimina una tarea
- Backend responde OK (200)
- Pero la tarea sigue apareciendo en pantalla
```

### Solución

**Actualizar el estado local después de la acción:**

```typescript
// ❌ MAL - No actualiza el UI
remove(id: number) {
    this.tasksService.remove(id).subscribe(() => {
        // ← No actualiza this.tasks
    });
}

// ✅ BIEN - Actualiza this.tasks
remove(id: number) {
    this.tasksService.remove(id).subscribe({
        next: () => {
            // Filtrar la tarea eliminada de la lista local
            this.tasks = this.tasks.filter(t => t.id !== id);
        },
        error: () => {
            this.error = 'No se pudo eliminar la tarea';
        }
    });
}

// También aplicable a actualizar
completed(task: Task) {
    const updatedTask = { ...task, completada: !task.completada };
    
    this.tasksService.update(updatedTask).subscribe({
        next: () => {
            // Actualizar en la lista local
            this.tasks = this.tasks.map(t => 
                t.id === task.id ? updatedTask : t
            );
        },
        error: () => {
            this.error = 'No se pudo actualizar la tarea';
        }
    });
}
```

---

# Errores de Estado

## ❌ Error 18: "No hay estados de loading y error en componentes"

### Síntomas
```
- App se siente lenta
- Usuario no sabe si está cargando o no
- Errores no se muestran
```

### Solución

**Agregar estados de loading y error:**

```typescript
// ❌ MAL - Sin estados
export class TasksComponent {
    tasks: Task[] = [];
}

// ✅ BIEN - Con estados
export class TasksComponent {
    tasks: Task[] = [];
    loading = false;
    error: string | null = null;
    
    ngOnInit(): void {
        this.loading = true;  // ← Comenzar carga
        
        this.tasksService.list().subscribe({
            next: (data) => {
                this.tasks = data;
                this.loading = false;  // ← Terminar carga
            },
            error: (err) => {
                this.error = "Error al cargar las tareas";
                this.loading = false;
            }
        });
    }
}
```

**Mostrar en template:**

```html
<!-- Mostrar spinner mientras carga -->
<div *ngIf="loading" class="spinner">
    Cargando...
</div>

<!-- Mostrar error si hay -->
<div *ngIf="error" class="error-box">
    ⚠️ {{ error }}
</div>

<!-- Mostrar contenido si no está cargando y no hay error -->
<div *ngIf="!loading && !error">
    <div *ngFor="let task of tasks">
        {{ task.titulo }}
    </div>
</div>

<!-- Mostrar mensaje si no hay tareas -->
<div *ngIf="!loading && !error && tasks.length === 0">
    📭 No hay tareas
</div>
```

---

## ❌ Error 19: "Después de crear una tarea, vuelve a la lista pero no aparece"

### Síntomas
```
- Usuario crea una tarea
- Se redirige a /tareas
- Pero la nueva tarea no aparece en la lista
```

### Solución

**Opción 1: Recargar la lista después de crear**

```typescript
// pages/task-new/task-new.component.ts
export class TaskNewComponent {
    constructor(
        private tasksService: TasksApiService,
        private router: Router
    ) {}
    
    save() {
        if (this.form.invalid) return;
        
        this.tasksService.create(this.form.getRawValue()).subscribe({
            next: (newTask) => {
                console.log('Tarea creada:', newTask);
                // Opción A: Navegar a tareas (que recargará la lista)
                this.router.navigateByUrl('/tareas');
            }
        });
    }
}

// pages/tasks/tasks.component.ts
export class TasksComponent implements OnInit {
    ngOnInit(): void {
        // Esto se ejecuta cada vez que el componente se crea
        // Así que cuando navegamos a /tareas, recarga la lista
        this.loadTasks();
    }
    
    private loadTasks() {
        this.tasksService.list().subscribe({
            next: (data) => {
                this.tasks = data;
            }
        });
    }
}
```

**Opción 2: Usar un servicio compartido con comportamiento**

```typescript
// services/tasks.service.ts
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TasksService {
    private tasksSubject = new BehaviorSubject<Task[]>([]);
    tasks$ = this.tasksSubject.asObservable();
    
    constructor(private api: TasksApiService) {}
    
    loadTasks() {
        this.api.list().subscribe(tasks => {
            this.tasksSubject.next(tasks);
        });
    }
    
    addTask(task: Task) {
        const current = this.tasksSubject.value;
        this.tasksSubject.next([...current, task]);
    }
}

// En el componente de crear
this.tasksService.addTask(newTask);

// En el componente de lista
this.tasks$ = this.tasksService.tasks$;
```

---

# Errores de Transformación de Datos

## ❌ Error 20: "Backend devuelve título, pero componente espera titulo"

### Síntomas
```
TypeError: Cannot read property 'titulo' of undefined
Propiedad 'titulo' no existe en type
```

### Solución

**Asegúrate que el servicio transforma correctamente:**

```typescript
// ❌ MAL - Sin transformación
@Injectable({ providedIn: 'root' })
export class TasksApiService {
    list(): Observable<Task[]> {
        // Backend devuelve: { id, title, description, completed }
        // Pero Task espera: { id, titulo, descripcion, completada }
        return this.http.get<Task[]>(this.baseUrl);
        // ← Error: tipos no coinciden
    }
}

// ✅ BIEN - Con transformación
interface TaskBackend {
    id: number;
    title: string;
    description?: string;
    completed: boolean;
}

@Injectable({ providedIn: 'root' })
export class TasksApiService {
    private baseUrl = 'http://localhost:8080/api/v1/tasks';
    
    private fromBackend(backendTask: TaskBackend): Task {
        return {
            id: backendTask.id,
            titulo: backendTask.title,              // ← Transformar
            descripcion: backendTask.description,   // ← Transformar
            completada: backendTask.completed       // ← Transformar
        };
    }
    
    list(): Observable<Task[]> {
        return this.http.get<TaskBackend[]>(this.baseUrl).pipe(
            map(tasks => tasks.map(t => this.fromBackend(t)))  // ← Map
        );
    }
}
```

---

## ❌ Error 21: "Al actualizar, se envía al backend con propiedades incorrectas"

### Síntomas
```
POST /api/v1/tasks
Body: {
  "titulo": "Mi tarea",      // ← Backend espera "title"
  "descripcion": "Desc",     // ← Backend espera "description"
  "completada": false        // ← Backend espera "completed"
}

Backend responde: 400 Bad Request - Unknown properties
```

### Solución

**Transformar antes de enviar:**

```typescript
// ❌ MAL - Enviar directo
create(data: NewTask): Observable<Task> {
    return this.http.post<Task>(this.baseUrl, data);
    // ← Envía { titulo, descripcion, completada }
    // Pero backend espera { title, description, completed }
}

// ✅ BIEN - Transformar antes de enviar
private toBackend(task: Task | NewTask): any {
    return {
        title: task.titulo,              // ← Transformar
        description: task.descripcion,   // ← Transformar
        completed: task.completada       // ← Transformar
    };
}

create(data: NewTask): Observable<Task> {
    return this.http.post<TaskBackend>(
        this.baseUrl,
        this.toBackend(data)  // ← Transformar primero
    ).pipe(
        map(response => this.fromBackend(response))  // ← Transformar response
    );
}
```

---

## ❌ Error 22: "Tipos de datos no coinciden - TS error"

### Síntomas
```
Type 'Task' is not assignable to type 'NewTask'
Type 'TaskBackend' is missing property 'titulo'
```

### Solución

**Definir tipos correctamente:**

```typescript
// ❌ MAL - Tipos mal definidos
export interface Task {
    id: number;
    titulo: string;
}

export interface NewTask {
    id: number;
    titulo: string;
}

// ✅ BIEN - NewTask omite id
export interface Task {
    id: number;
    titulo: string;
    descripcion?: string;
    completada: boolean;
}

export type NewTask = Omit<Task, 'id'>;

// Ahora:
// Task = { id, titulo, descripcion, completada }
// NewTask = { titulo, descripcion, completada }
```

---

## 🎯 Resumen Rápido de Errores Más Comunes

| # | Error | Causa Raíz | Solución Rápida |
|---|-------|-----------|-----------------|
| 1 | 401 Unauthorized siempre | Credenciales o URL incorrecta | Verificar backend y endpoint |
| 3 | Token no se guarda | Interceptor no registrado | Agregar en app.config.ts |
| 4 | Token no se envía | Interceptor no funciona | Registrar correctamente |
| 5 | Token expirado no detecta | No maneja 401 | Agregar catchError en interceptor |
| 7 | No autenticado accede a rutas | Falta guard | Agregar canActivate: [AuthGuard] |
| 12 | Memory leak | No desuscribir | Usar takeUntil o async pipe |
| 14 | Formulario inválido enviado | Sin validación | Agregar markAllAsTouched() |
| 16 | Página vacía | Falta ngOnInit | Agregar y llamar al servicio |
| 17 | Cambios no se ven | No actualizar estado local | Actualizar this.tasks después |
| 20 | Propiedades undefined | No transformar datos | Agregar map() en observable |

---

## 💡 Checklist Antes de Entregar

- ✅ Interceptor registrado en app.config.ts
- ✅ AuthGuard en rutas protegidas
- ✅ PublicGuard en /login
- ✅ Formularios con Validators.required y Validators.minLength
- ✅ Estados loading y error en componentes
- ✅ Transformación de datos (fromBackend, toBackend)
- ✅ Manejo de 401 en interceptor
- ✅ Actualización de estado local después de crear/editar/eliminar
- ✅ Desuscripciones correctas (async pipe o takeUntil)
- ✅ Tipos de datos correctos (NewTask sin id)
- ✅ ngOnInit() cargando datos
- ✅ Cambios se reflejan en UI

---

**Última actualización:** Enero 2026
