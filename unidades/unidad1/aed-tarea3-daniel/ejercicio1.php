<?php
/**
 * 1) Hola fichero (crear y leer)
 * Enunciado: Crea un fichero datos.txt con el texto 
 * "Hola Mundo desde PHP" y, a continuación, léelo y muestra su contenido por pantalla.
 * Funciones/Comandos sugeridos: file_put_contents, file_get_contents
 * @author danielrguezh
 * @version 1.0.0
 */
    file_put_contents("resources/datos.txt", "Hola Mundo desde PHP");
    echo file_get_contents("resources/datos.txt");
?>