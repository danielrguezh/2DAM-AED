# Plan de Estudio para el Examen

Aquí tienes una estrategia probada para dominar este ejercicio y sacar un 10.

## Fase 1: Entender (Lectura - 30 min)
Antes de tocar código, asegúrate de que entiendes qué hace cada pieza.
1.  Lee **`01_arquitectura_e_implementacion.md`**: Entiende el flujo "Login -> Token -> Guardar Token -> Interceptor lo envía".
2.  Lee **`04_conceptos_teoricos_deep_dive.md`**: Por si el profesor hace preguntas teóricas ("¿Qué es un Guard?").

## Fase 2: "Tabula Rasa" (Práctica - 2 horas)
Esta es la prueba de fuego.
1.  Crea un proyecto nuevo de Angular (`ng new practica-examen`).
2.  Intenta **replicar el proyecto** sin mirar el código original (o mirando lo mínimo posible).
3.  Usa la **`05_chuleta_http_fetch.md`** como referencia para la sintaxis.
4.  **Objetivo**: Conseguir que haga Login y liste las tareas.

> *Si te atascas, mira `03_errores_comunes_y_debugging.md` antes de mirar la solución.*

## Fase 3: Simulacro de Examen (Desafíos)
El día del examen te darán una base y te pedirán cambios. Entrena con estos escenarios (mira `02_variaciones_examen.md` para pistas):

### Desafío A: "El usuario VIP"
*Requisito*: "Añade un campo `vip` (boolean) al Login. Si el usuario es VIP, pon el fondo de la lista de tareas en dorado."
- **Pista**: Tienes que tocar el `LoginResponse` (model), `LoginComponent` (HTML/TS) y `Navbar` o `Tasks` (CSS).

### Desafío B: "Categorías"
*Requisito*: "Las tareas ahora tienen una categoría (Trabajo, Casa). Muestra un icono diferente según la categoría."
- **Pista**: Modifica la interfaz `Task`, el HTML de tareas y añade lógica con `@if` o `[ngClass]`.

### Desafío C: "La papelera de reciclaje"
*Requisito*: "En lugar de borrar la tarea definitivamente, márcala como `borrada: true` y no la muestres en la lista principal."
- **Pista**: Es un filtrado en el frontend (`tasks.filter(t => !t.borrada)`).

## Resumen de Recursos (Tu Kit de Supervivencia)
Tener estos archivos a mano durante el examen vale oro:
- **`01_...`**: Para saber dónde va cada fichero.
- **`03_...`**: Para cuando te salga el error 404/403 y entres en pánico.
- **`05_...`**: Para copiar y pegar los `http.get` y `http.post`.
