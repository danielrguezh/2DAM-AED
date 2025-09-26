<?php
/**
 * 11) Diario personal secreto
 * Enunciado: Guarda entradas con fecha y hora en diario.txt.
 * Luego muéstralas todas.
 * @author danielrguezh
 * @version 1.0.0
 */
    function diario($entrada){
        $rutaArchivo = "resources/ejercicio11/diario.txt";
        $archivo = fopen($rutaArchivo, "a");

        $fechaActual = date('[Y-m-d H:i:s]');
        $entradaFormato = "$fechaActual $entrada \n";

        fwrite($archivo, "$entradaFormato");

        echo file_get_contents($rutaArchivo);
        fclose($archivo);
   }

   diario("Hola, estoy programando");
?>