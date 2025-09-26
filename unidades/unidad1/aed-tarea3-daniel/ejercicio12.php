<?php
/**
 * 12) Ranking de videojuegos
 * Enunciado: Guarda juegos con puntuaciones en ranking.txt,
 * ordénalos y muestra el top 3.
 * @author danielrguezh
 * @version 1.0.0
 */

function guardarPuntuacion(array $juegos): void {
    $path = "resources/ejercicio12/ranking.txt";
    $file = fopen($path, "w");
    foreach ($juegos as $juego => $puntuacion) {
        fwrite($file, "$juego: $puntuacion\n");
    }
    fclose($file);
} 

function leerListaJuegos(): void {
    $path = "resources/ranking.txt";
    $lineas = file($path, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

    $ranking = [];
    foreach ($lineas as $linea) {
        [$juego, $puntos] = explode(":", $linea);
        $ranking[trim($juego)] = (int) trim($puntos);
    }

    // Ordenar de mayor a menor puntuación
    arsort($ranking);

    // Mostrar el top 3
    $top = array_slice($ranking, 0, 3, true);
    $pos = 1;
    foreach ($top as $juego => $puntos) {
        echo "$pos. $juego ($puntos puntos)\n";
        $pos++;
    }
}

$juegos = [
    "Zelda" => 10, 
    "Sonic" => 8,
    "Mario" => 9,
    "For Honor" => 9
];

guardarPuntuacion($juegos);
leerListaJuegos();

?>