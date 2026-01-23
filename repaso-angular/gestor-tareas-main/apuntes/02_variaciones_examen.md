# Variaciones y Ampliaciones para Examen

Este documento explica cómo abordar requisitos adicionales que podrían pedirte en un examen o ampliación de la práctica.

## 1. Gestión de Roles (Admin vs User)

Si te piden que **solo un administrador** pueda hacer ciertas acciones (ej. borrar tareas).

### Pasos:
1.  **Backend**: Asegúrate que el endpoint devuelve el rol. En nuestra app, `LoginResponse` ya tiene un campo `roles`.
2.  **AuthService**:
    - Guarda los roles junto con el token en `login()`.
    - Crea un método `isAdmin(): boolean`.
      ```typescript
      isAdmin(): boolean {
        const roles = JSON.parse(localStorage.getItem('user_roles') || '[]');
        return roles.includes('ROLE_ADMIN');
      }
      ```
3.  **Componente (HTML)**:
    - Inyecta `AuthService` (o úsalo público).
    - Usa `@if` para ocultar botones.
      ```html
      @if (auth.isAdmin()) {
        <button (click)="borrar(tarea.id)">Borrar Tarea</button>
      }
      ```

---

## 2. Añadir una Nueva Entidad (Ej. "Categorías")

Si te piden gestionar algo que NO son tareas (ej. `Category` o `Project`).

### Pasos:
1.  **Crear Modelo**: `src/app/models/category.model.ts`
    ```typescript
    export interface Category { id: number; name: string; }
    ```
2.  **Crear Servicio**: `ng g s services/categories-api`
    - Copia la estructura de `tasks-api.service.ts`.
    - Cambia la URL base a `/api/v1/categories`.
3.  **Crear Componente**: `ng g c pages/categories`
    - Inyecta el nuevo servicio y muestra la lista igual que en `TasksComponent`.
4.  **Ruta**: Añade `{ path: 'categorias', component: CategoriesComponent, canActivate: [authGuard] }` en `app.routes.ts`.

---

## 3. Editar Tarea (Update / PUT)

Si te piden poder modificar una tarea existente.

### Pasos:
1.  **Servicio**: Ya tenemos el método `update(id, data)` en `TasksApiService`.
2.  **Ruta**: Añade una ruta con parámetro: `{ path: 'tareas/editar/:id', component: TaskNew }`.
3.  **Componente `TaskNew`**:
    - Detecta si hay un ID en la URL (`ActivatedRoute`).
    - Si hay ID:
      - Carga la tarea (`getById`) y rellena el formulario (`this.form.patchValue(tarea)`).
      - Cambia la función `save()` para que llame a `update()` en vez de `create()`.

---

## 4. Filtros en el Listado

Si te piden filtrar tareas por completadas/pendientes.

### Pasos:
1.  **HTML**: Añade un `<select>` o botones.
    ```html
    <button (click)="filtro='todas'">Todas</button>
    <button (click)="filtro='pendientes'">Pendientes</button>
    ```
2.  **TypeScript**:
    - Crea una variable `filtro = 'todas'`.
    - Crea un `computed signal` o un `getter` que filtre el array `tasks`.
    ```typescript
    get tareasFiltradas() {
      if (this.filtro === 'todas') return this.tasks;
      return this.tasks.filter(t => this.filtro === 'pendientes' ? !t.completada : t.completada);
    }
    ```
3.  **HTML**: Itera sobre `tareasFiltradas` en vez de `tasks`.

---

## 5. Validación Avanzada

Si te piden validaciones extra en el formulario (ej. fecha futura, email válido).

### Pasos:
- En `TaskNew`, añade validadores al `FormGroup`.
- Angular tiene validadores listos (`Validators.email`, `Validators.pattern`).
- Para mensajes de error, mira `form.controls['campo'].errors`.
