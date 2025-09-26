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

function leer3Mejores(): void {
    $path = "resources/ejercicio12/ranking.txt";
    $lineas = file($path);

    $ranking = [];
    foreach ($lineas as $linea) {
        [$juego, $puntos] = explode(":", $linea);
        $ranking[trim($juego)] = (int) trim($puntos);
    }

    arsort($ranking);
    $top = array_slice($ranking, 0, 3);
    $posicion = 1;
    foreach ($top as $juego => $puntos) {
        echo "$posicion. $juego ($puntos puntos)\n";
        $posicion++;
    }
}

$juegos = [
    "Zelda" => 10, 
    "Sonic" => 8,
    "Mario" => 9,
    "For Honor" => 9
];

guardarPuntuacion($juegos);
leer3Mejores();

?>