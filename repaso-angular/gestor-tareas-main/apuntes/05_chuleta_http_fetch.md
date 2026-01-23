# Chuleta: Peticiones HTTP (HttpClient)

En Angular no usamos `fetch()`, usamos `HttpClient` que devuelve **Observables**.
Aquí tienes "padrones" de copiar y pegar.

## 1. Inyección (Setup)
Siempre necesitas inyectar el cliente HTTP.

```typescript
// Opción moderna (Angular 14+)
private http = inject(HttpClient);

// Opción clásica (Constructor)
constructor(private http: HttpClient) {}
```

---

## 2. GET: Pedir datos
### Pedir una lista (Array)
```typescript
list(): Observable<Task[]> {
  return this.http.get<Task[]>('http://api.com/tasks');
}
```

### Pedir un solo elemento (por ID)
```typescript
getById(id: number): Observable<Task> {
  return this.http.get<Task>(`http://api.com/tasks/${id}`);
}
```

---

## 3. POST: Enviar datos (Crear)
Se envían **2 argumentos**: URL y el objeto con datos (body).
```typescript
create(datos: object): Observable<Task> {
  return this.http.post<Task>('http://api.com/tasks', datos);
}
```

---

## 4. DELETE: Borrar
Solo necesita la URL con el ID.
```typescript
delete(id: number): Observable<void> {
  return this.http.delete<void>(`http://api.com/tasks/${id}`);
}
```

---

## 5. PUT vs PATCH: Actualizar
- **PUT**: Reemplaza **TODO** el objeto. (Si falta un campo, lo borra).
- **PATCH**: Reemplaza **SOLO** los campos que envías.

```typescript
// PUT: Cuidado, envía TODO el objeto
updatePut(id: number, tarea: Task): Observable<Task> {
  return this.http.put<Task>(`http://api.com/tasks/${id}`, tarea);
}

// PATCH: Ideal para cambiar solo un campo (ej. completar: true)
updatePatch(id: number, cambios: Partial<Task>): Observable<Task> {
  return this.http.patch<Task>(`http://api.com/tasks/${id}`, cambios);
}
```

---

## 6. Consumir (Usar) el Servicio
Las peticiones **NO** se lanzan hasta que haces `.subscribe()`.

```typescript
this.service.list().subscribe({
  next: (datos) => {
    console.log('Éxito:', datos);
    this.lista = datos;
  },
  error: (err) => {
    console.error('Error:', err);
    alert('Falló la conexión');
  },
  complete: () => {
    console.log('Terminó (opcional)');
  }
});
```
