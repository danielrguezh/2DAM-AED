# Conceptos Teóricos "Deep Dive" (Para sacar nota)

Si el profesor te pregunta "¿Por qué usamos...?" o "¿Cómo funciona realmente...?", aquí tienes las respuestas técnicas.

## 1. El Patrón "Interceptor"
**Pregunta**: *¿Por qué no ponemos el token manualmente en cada `http.get`?*

**Respuesta Técnica**:
Usamos un **HttpInterceptor** porque implementa el patrón de diseño "Chain of Responsibility" (Cadena de Responsabilidad) o "Middleware".
Nos permite interceptar el tráfico HTTP en un único punto centralizado.
- **Ventaja 1**: Mantenibilidad. Si cambiamos la forma de autenticar (ej. de Bearer a Basic), solo cambiamos un archivo.
- **Ventaja 2**: Seguridad. Garantizamos que *todas* las peticiones llevan seguridad sin despistes.

**Código clave**:
```typescript
req.clone({ setHeaders: { Authorization: ... } })
```
> *Nota: Los objetos Request son inmutables en Angular. Por eso usamos `clone()`.*

---

## 2. Inyección de Dependencias (DI)
**Pregunta**: *¿Qué es ese `inject(AuthService)`?*

**Respuesta Técnica**:
Es el mecanismo de Angular para entregarnos las instancias de las clases que necesitamos (Servicios) sin que nosotros tengamos que hacer `new AuthService()`.
- **Singleton**: Por defecto (`providedIn: 'root'`), Angular crea **una sola instancia** del servicio para toda la app. Esto es vital para mantener el estado (como el token o si el usuario está logueado) compartido entre componentes.

---

## 3. Guards y Ciclo de Vida del Router
**Pregunta**: *¿Cuándo se ejecuta el AuthGuard?*

**Respuesta Técnica**:
Se ejecuta durante la fase de **Navegación** del Angular Router, antes de que se cargue el componente destino.
1.  URL Change detectado.
2.  Route Matching (busca qué ruta encaja).
3.  **Guards Check** (`canActivate`). <- Aquí entramos nosotros.
    - Si devuelve `true` -> Sigue cargando (Resolvers -> Componente).
    - Si devuelve `false` o `UrlTree` -> Cancela y redirige.

---

## 4. JWT (JSON Web Token)
**Pregunta**: *¿Qué es ese string largo que guardamos?*

**Respuesta Técnica**:
Es un estándar (RFC 7519) para compartir información firmada. Tiene 3 partes separadas por puntos (`.`):
1.  **Header**: Algoritmo de encriptación (ej. HS256).
2.  **Payload**: Datos del usuario (Claims). Aquí viene el `sub` (username), roles, expiración (`exp`), etc.
3.  **Signature**: Firma digital del servidor para autenticar que el token no ha sido modificado.

**Importante**: El Frontend trata el token como una "caja negra" (solo lo guarda y lo envía), a menos que necesite leer el rol o la expiración.

---

## 5. Observables vs Promesas
**Pregunta**: *¿Por qué usamos `subscribe()` y no `await`?*

**Respuesta Técnica**:
Angular usa **RxJS (Reactive Extensions)**. `HttpClient` devuelve **Observables**.
- **Promesa**: Resuelve un solo valor una vez. (Eager).
- **Observable**: Es un flujo de datos en el tiempo. (Lazy: no se ejecuta hasta que haces `.subscribe()`).
- Permite operaciones poderosas como `pipe()`, `map()`, `catchError()`, cancelar peticiones en vuelo, etc.

En esta práctica usamos `.subscribe({ next: ..., error: ... })` para reaccionar a la respuesta del servidor.
