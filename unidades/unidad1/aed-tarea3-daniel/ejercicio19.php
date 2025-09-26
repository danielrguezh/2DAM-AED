<?php
/**
 * 19) Simulador de tweets
 * Enunciado: Guarda tweets en tweets.txt con fecha y hora, muestra los últimos 5.
 * @author danielrguezh
 * @version 1.0.0
 */

function crearTwitt($entrada){
   $rutaArchivo = "resources/ejercicio19/tweets.txt";
   $archivo = fopen($rutaArchivo, "a");

   $fechaActual = date('[Y-m-d H:i:s]');
   $entradaFormato = "$fechaActual $entrada\n";

   fwrite($archivo, $entradaFormato);
   fclose($archivo);

}

function mostrarUltimosTwitts($n = 5){
    $contenido = file_get_contents("resources/ejercicio19/tweets.txt");
    $twitts = array_filter(explode("\n", $contenido));

    
    $ultimos = array_slice($twitts, -$n);

    echo " Últimos $n twitts:\n";
    foreach (array_reverse($ultimos) as $twitt) {
        echo $twitt . "\n";
    }
}

crearTwitt("Hola, estoy programando otra vez");
mostrarUltimosTwitts();
?>