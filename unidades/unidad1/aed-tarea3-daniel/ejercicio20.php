<?php
/**
 * 20) Historias locas (Mad Libs)
 * Enunciado: Reemplaza placeholders en plantilla.txt con palabras aleatorias de otros ficheros.
 * @author danielrguezh
 * @version 1.0.0
 */
$plantilla = file_get_contents("resources/ejercicio20/plantilla.txt");

$animales = explode("\n",file_get_contents("resources/ejercicio20/animales.txt"));
$lugares  = explode("\n",file_get_contents("resources/ejercicio20/lugares.txt"));
$comidas  = explode("\n",file_get_contents("resources/ejercicio20/comidas.txt"));

$plantilla = str_replace("[animal]", $animales[array_rand($animales)], $plantilla);
$plantilla = str_replace("[lugar]", $lugares[array_rand($lugares)], $plantilla);
$plantilla = str_replace("[comida]", $comidas[array_rand($comidas)], $plantilla);

echo $plantilla;
?>