<?php
/**
 * Ejercicio 2: Estadísticas de palabras en PHP
 * Dado un fichero texto.txt, contar cuántas palabras hay en total y cuántas veces aparece cada palabra.z
 * a salida se debe guardar en estadisticas.csv con el formato:
 * palabra,frecuencia
 * Reglas:
 *  - Ignorar mayúsculas/minúsculas (ejemplo: PHP y php cuentan como la misma palabra).
 *  - Ignorar signos de puntuación.
 * @author danielrguezh
 * @version 1.0.0
 */

 $contenido = file_get_contents("resources/ejercicio2/texto.txt");

function contarPalabras($contenido) : void {
    $contenido = strtolower($contenido);
    $contenido = preg_replace("/[[:punct:]]/u", " ", $contenido);

    $palabras = preg_split('/\s+/', $contenido, -1, PREG_SPLIT_NO_EMPTY);
    $frecuencias = array_count_values($palabras);

    $file = fopen("resources/ejercicio2/estadisticas.csv", "w");
    fwrite($file, "palabra,frecuencia\n");
    foreach ($frecuencias as $palabra => $frecuencia) {
        fwrite($file, "$palabra,$frecuencia\n");
    }
    fclose($file);
}

contarPalabras($contenido);
?>