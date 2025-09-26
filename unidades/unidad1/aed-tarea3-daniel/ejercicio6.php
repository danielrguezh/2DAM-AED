<?php
/**
 * 6) Invertir el contenido de un fichero
 * Enunciado: Lee frase.txt, invierte el texto y guarda el resultado en frase_invertida.txt.
 * Funciones sugeridas: file_get_contents, strrev, file_put_contents
 * @author danielrguezh
 * @version 1.0.0
 */
$file="resources/ejercicio6/frase.txt";
$fileReverse="resources/ejercicio6/frase_invertida.txt";
$contenido=file_get_contents($file);
file_put_contents($fileReverse,strrev($contenido));
?>