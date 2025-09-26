<?php
/**
 * 10) Leer JSON desde fichero
 * Enunciado: Crea datos.json con información de personas (nombre y edad).
 * Haz que el programa lo lea y muestre los datos.
 * Funciones sugeridas: file_get_contents, json_decode
 * @author danielrguezh
 * @version 1.0.0
 */
function leerJSON(){
    $rutaArchivo = "resources/ejercicio10/datos.json";
    $archivo = fopen($rutaArchivo, "r");

    $datosJson = file_get_contents($rutaArchivo);
    $datos = json_decode($datosJson);
    fclose($archivo);
    print_r(value: $datos);
}

leerJSON();
?>