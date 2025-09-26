<?php
/**
 * 13) Canción aleatoria
 * Enunciado: Guarda títulos en canciones.txt y muestra uno al azar.
 * @author danielrguezh
 * @version 1.0.0
 */
function guardarCanciones(array $canciones): void {
    $file = fopen("resources/ejercicio13/canciones.txt", "w");
    foreach ($canciones as $cancion) {
        fwrite($file, "$cancion\n");
    }
}

function obtenerCancionRandom(): string {
    $file = file_get_contents("resources/ejercicio13/canciones.txt");
    $canciones = explode("\n", $file);
    $index = array_rand($canciones);
    return $canciones[$index];
}

$canciones = ["Hysteria", "Bohemian Rhapsody", "Africa"];
guardarCanciones($canciones);
echo obtenerCancionRandom();
?>