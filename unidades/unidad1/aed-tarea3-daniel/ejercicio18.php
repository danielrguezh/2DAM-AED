<?php
/**
 * 18) Encuesta de comidas favoritas
 * Enunciado: Guarda respuestas de usuarios en comidas.txt y genera ranking.
 * @author danielrguezh
 * @version 1.0.0
 */
$archivo = "resources/ejercicio18/comidas.txt";

function agregarComida(string $archivo, string $comida): void {
    $comida = trim($comida);
    if ($comida === "") return;
    file_put_contents($archivo, $comida . PHP_EOL, FILE_APPEND);
}

function mostrarRankingDesdeArchivo(string $archivo): void {
    if (!file_exists($archivo)) {
        echo "El archivo no existe.\n";
        return;
    }

    $lineas = file($archivo);
    $ranking = [];
    foreach ($lineas as $linea) {
        $comida = trim($linea);
        if ($comida === "") continue;
        if (isset($ranking[$comida])) {
            $ranking[$comida]++;
        } else {
            $ranking[$comida] = 1;
        }
    }

    arsort($ranking);
    $posicion = 1;
    foreach ($ranking as $comida => $veces) {
        echo "$posicion. $comida: $veces\n";
        $posicion++;
    }
}

$comida = readline("Agregue su comida favorita ");
agregarComida($archivo, $comida);
mostrarRankingDesdeArchivo($archivo);

?>