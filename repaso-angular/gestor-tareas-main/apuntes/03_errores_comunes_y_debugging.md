# Guía de Debugging y Errores Comunes

Si algo no funciona, sigue esta lista de comprobación.

## 1. "No me hace Login" / Error 404 / Error 403

### Síntomas:
- Mensaje rojo en pantalla.
- Consola del navegador dice `404 Not Found` o `403 Forbidden`.

### Causa Probable:
- **404**: La URL está mal. Probablemente Angular no está usando el Proxy.
  - **Solución**: Asegúrate de que `angular.json` tiene `"proxyConfig": "proxy.conf.json"` y que has **REINICIADO** `ng serve`.
- **403**: Problema de seguridad.
  - El backend requiere autenticación y no estás enviando token (revisa Interceptor).
  - Estás intentando acceder al puerto 8080 directamente sin proxy (CORS).
- **Backend apagado**: Si la consola dice `Connection refused`, es que `mvn spring-boot:run` no está corriendo.

### Credenciales Correctas:
- **User**: `admin` / **Pass**: `admin123`

---

## 2. "No me cargan las tareas" (Lista vacía)

### Comprobaciones:
1.  Abre las **Herramientas de Desarrollador** (F12) -> Pestaña **Network (Red)**.
2.  Refresca la página. Busca la petición `tasks`.
3.  **¿Está en rojo?**: Mira el código de error.
    - **401 Unauthorized**: Tu token ha caducado o no se envía. Haz Logout y Login de nuevo.
    - **500 Internal Server Error**: El backend falló. Mira la terminal de Java para ver el error real.
4.  **¿Está en verde (200 OK) pero vacío?**:
    - Quizás no tienes tareas en la base de datos. Crea una tarea nueva.

---

## 3. "El formulario no me deja guardar"

### Síntomas:
- Botón "Guardar" deshabilitado o no hace nada.

### Causa Probable:
- **Validación**: Algún campo es inválido.
- **Debug**: En el código `save()`, pon un `console.log(this.form.value, this.form.valid)`.
- Revisa los requisitos: Título min 3 caracteres, etc.

---

## 4. "Puerto 8080 ocupado" (Backend no arranca)

### Mensaje:
`Web server failed to start. Port 8080 was already in use.`

### Solución:
Tienes un proceso Java antiguo zombie.
1.  Busca el proceso: `lsof -i :8080`
    ```bash
    COMMAND   PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
    java    12345 fran   ...
    ```
2.  Mátalo: `kill -9 12345` (cambia 12345 por el PID real).
3.  Arranca de nuevo: `mvn spring-boot:run`.

---

## 5. Check List de Pánico (Si nada funciona)

1.  [ ] **Reiniciar todo**:
    - Para `ng serve` (Ctrl+C) y arranca de nuevo.
    - Para `mvn spring-boot:run` (Ctrl+C) y arranca de nuevo.
2.  [ ] **Limpiar navegador**: Abre una ventana de Incógnito para asegurar que no hay caché o tokens viejos corruptos.
3.  [ ] **Proxy**: Verifica que `http://localhost:4200/api/v1/tasks` responde (te debería pedir login o dar 401, pero responder). Si da 404, el proxy está mal.
