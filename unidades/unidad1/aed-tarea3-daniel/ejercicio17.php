<?php
/**
 * 17) Generador de nombres para superhéroes
 * Enunciado: Combina palabras de adjetivos.txt y animales.txt.
 * @author danielrguezh
 * @version 1.0.0
 */
$adjetivos = file("resources/ejercicio17/adjetivos.txt");
$animales = file("resources/ejercicio17/animales.txt");

$adj = $adjetivos[array_rand($adjetivos)];
$animal = $animales[array_rand($animales)];

$combinacion = trim($adj) . " " . trim($animal);
echo "$combinacion\n";
?>