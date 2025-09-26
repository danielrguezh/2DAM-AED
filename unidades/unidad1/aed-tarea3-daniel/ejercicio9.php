<?php
/**
 * 9) Registrar entradas de usuario
 * Enunciado: Guarda los nombres recibidos en un formulario dentro de usuarios.txt
 * (cada nombre en una línea). Luego muéstralos.
 * Funciones sugeridas: fopen, fwrite, file
 * @author danielrguezh
 * @version 1.0.0
 */
function registrarEntrada(){
    $arrayNombres = ["Ana", "Pedro", "Lucia"];
    $rutaArchivo = "resources/ejercicio9/usuarios.txt";
    $archivo = fopen($rutaArchivo, "w");
    for ($i = 0; $i <= sizeof($arrayNombres)-1; $i++){
    fwrite($archivo, "$arrayNombres[$i] \n");
    }

    echo file_get_contents($rutaArchivo);
    fclose($archivo);
}

registrarEntrada();
?>