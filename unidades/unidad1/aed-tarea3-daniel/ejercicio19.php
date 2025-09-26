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
   $entradaFormato = "$fechaActual $entrada \n";

   fwrite($archivo, "$entradaFormato");

   echo file_get_contents($rutaArchivo);
   fclose($archivo);
}

function mostrarUltimosTwitts(){
    $twitts= explode("\n",file_get_contents("resources/ejercicio19/tweets.txt"));
    for ($i=sizeof($twitts)-1; $i >= sizeof($twitts)-5; $i--) { 
        pprint($twitts[$i]);
    }
}
crearTwitt("Hola, estoy programando otra vez");

?>