<?php
/**
 * 3) Contar palabras en un fichero
 * Enunciado: Escribe un texto en texto.txt, luego haz que tu programa
 * cuente cuántas palabras contiene ese archivo.
 * Funciones sugeridas: file_get_contents, str_word_count
 * @author danielrguezh
 * @version 1.0.0
 */
$file="resources/ejercicio3/texto.txt";
$datos=file_get_contents($file);
echo str_word_count($datos);
?>