# Guía Técnica de Implementación: Gestor de Tareas (Angular + Spring Boot JWT)

Esta guía detalla los pasos técnicos exactos realizados para implementar la aplicación de gestión de tareas con autenticación JWT, conectando un frontend Angular con un backend Spring Boot.

## 1. Configuración del Entorno de Desarrollo

### 1.1 Preparación del Backend
El backend es un proyecto Java/Spring Boot que expone una API REST en el puerto `8080`.
- **Ruta base**: `http://localhost:8080/api/v1`
- **Autenticación**: Endpoint `/auth/login` que devuelve un token JWT.
- **Endpoints Tareas**: `/tasks` (GET, POST, DELETE) protegidos con JWT.

**Ejecución**:
```bash
cd tasks-api
mvn spring-boot:run
```

### 1.2 Configuración del Proxy (CORS)
Para evitar bloqueos CORS y comunicar el puerto `4200` (Angular) con el `8080` (Java), se configura un proxy inverso en desarrollo.

1.  Crear [proxy.conf.json](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/proxy.conf.json) en la raíz:
    ```json
    {
      "/api": {
        "target": "http://localhost:8080",
        "secure": false,
        "changeOrigin": true
      }
    }
    ```
2.  Registrarlo en [angular.json](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/angular.json) (sección `architect > serve > options`):
    ```json
    "proxyConfig": "proxy.conf.json"
    ```

### 1.3 Variables de Entorno
En [src/environments/environment.ts](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/environments/environment.ts), definimos la URL base relativa para que use el proxy:
```typescript
export const environment = {
  apiBaseUrl: '/api/v1', // El proxy redirige esto a localhost:8080/api/v1
};
```

---

## 2. Implementación de la Autenticación

### 2.1 Modelos de Datos ([auth.model.ts](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/models/auth.model.ts))
Interfaces TypeScript para tipar las peticiones y respuestas:
```typescript
export interface LoginRequest { username: string; password: string; }
export interface LoginResponse { token: string; }
```

### 2.2 Servicio de Autenticación ([auth.service.ts](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/services/auth.service.ts))
Encargado de la lógica de negocio de sesión.
- **[login(credentials)](file:///Users/fran/Documents/GitHub/gestor-tareas/tasks-api/src/main/java/com/docencia/tasks/adapters/in/controller/AuthController.java#32-44)**: Realiza `POST /auth/login`. Si es exitoso, guarda el token.
- **Gestión del Token**: Se almacena en `localStorage` (`setItem('auth_token', token)`).
- **[logout()](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/services/auth.service.ts#28-32)**: Elimina el token de `localStorage` y redirige a `/login`.
- **[isLoggedIn()](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/services/auth.service.ts#32-35)**: Retorna `true` si existe un token almacenado.

### 2.3 Interceptor HTTP ([auth.interceptor.ts](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/interceptors/auth.interceptor.ts))
Intercepta **todas** las peticiones salientes para inyectar las credenciales.
1.  Obtiene el token del [AuthService](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/services/auth.service.ts#8-36).
2.  Si existe, clona la petición HTTP y añade la cabecera:
    `Authorization: Bearer <token_jwt>`
3.  Manejo de errores: Si el servidor devuelve **401 Unauthorized**, fuerza el [logout](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/services/auth.service.ts#28-32) para evitar estados inconsistentes.

**Registro**: Se añade en [app.config.ts](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/app.config.ts) dentro de `provideHttpClient(withInterceptors([authInterceptor]))`.

### 2.4 Guardia de Rutas ([auth.guard.ts](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/guards/auth.guard.ts))
Protege la navegación en el cliente.
- Se ejecuta antes de entrar a una ruta.
- Verifica `authService.isLoggedIn()`.
- Si es `false`, cancela la navegación y redirige a `/login`.

---

## 3. Desarrollo de Vistas (Pages)

### 3.1 Página de Login
- Formulario reactivo (`ReactiveFormsModule`) con campos `username` y [password](file:///Users/fran/Documents/GitHub/gestor-tareas/tasks-api/src/main/java/com/docencia/tasks/infrastructure/security/SecurityConfig.java#63-67).
- Al enviar (`submit`), llama a `authService.login()`.
- Manejo de errores: Muestra mensaje en pantalla si el backend devuelve error (403/401).

### 3.2 Página de Tareas (Protegida)
- Configurada en [app.routes.ts](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/app.routes.ts) con `canActivate: [authGuard]`.
- Utiliza [TasksApiService](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/services/tasks-api.ts#8-48) para obtener la lista de tareas.
- Gracias al interceptor, la petición GET ya incluye el token automáticamente.

---

## 4. Configuración de Rutas ([app.routes.ts](file:///Users/fran/Documents/GitHub/gestor-tareas/gestor-tareas-2/src/app/app.routes.ts))

Definición final del enrutamiento:

```typescript
export const routes: Routes = [
    { path: 'login', component: Login },
    // Rutas protegidas por el Guard
    { path: 'tareas', component: Tasks, canActivate: [authGuard] },
    { path: 'tareas/nueva', component: TaskNew, canActivate: [authGuard] },
    // Redirección por defecto
    { path: '', redirectTo: 'tareas', pathMatch: 'full' },
    { path: '**', redirectTo: 'tareas' },
];
```

## 5. Resumen del Flujo de Datos

1.  **Inicio**: Usuario entra a `localhost:4200`.
2.  **Guard**: Detecta que no hay token y redirige a `/login`.
3.  **Login**: Usuario envía credenciales. Backend valida y devuelve JWT. Angular lo guarda.
4.  **Navegación**: Usuario va a `/tareas`. Guard permite el paso (hay token).
5.  **Petición API**: `TasksComponents` pide tareas. `AuthInterceptor` pone el JWT en la cabecera.
6.  **Proxy**: Redirige la petición al puerto 8080.
7.  **Backend**: Valida el JWT y devuelve los datos JSON.
