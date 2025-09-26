<?php
/**
 * 18) Encuesta de comidas favoritas
 * Enunciado: Guarda respuestas de usuarios en comidas.txt y genera ranking.
 * @author danielrguezh
 * @version 1.0.0
 */
function guardarCanciones(array $canciones): void {
    $file = fopen("resources/ejercicio18/canciones.txt", "w");
    foreach ($canciones as $cancion) {
        fwrite($file, "$cancion\n");
    }
}

function obtenerCancionRandom(): string {
    $file = file_get_contents("resources/ejercicio18/canciones.txt");
    $canciones = explode("\n", $file);
    $index = array_rand($canciones);
    return $canciones[$index];
}

$canciones = ["Hysteria", "Bohemian Rhapsody", "Africa"];
guardarCanciones($canciones);
echo obtenerCancionRandom();
?>