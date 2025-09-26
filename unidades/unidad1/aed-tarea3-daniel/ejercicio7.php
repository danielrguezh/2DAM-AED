<?php
/**
 * 7) Calcular suma desde fichero
 * Enunciado: Guarda números separados por comas en datos_numericos.txt. Léelos y calcula su suma.
 * Funciones sugeridas: file_get_contents, explode, array_sum
 * @author danielrguezh
 * @version 1.0.0
 */
$file= "resources/ejercicio7/datos_numericos.txt";
$contenido=file_get_contents($file);
echo $contenido;
$contenido=explode(",",$contenido);
     
echo "\n" . array_sum($contenido);
?>