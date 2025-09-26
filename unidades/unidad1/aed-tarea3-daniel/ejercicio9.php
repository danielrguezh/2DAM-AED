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
    $rutaArchivo = "resources/ejercicio9/usuarios.txt";
    $archivo = fopen($rutaArchivo, "w");
    foreach ($nombres as $key => $value) {
        fwrite($file,"$value \n");
    }
    $nombres=file_get_contents($rutaArchivo);
    return $nombres;
}
echo crearLeer(["Ana", "Pedro", "Lucia"]);

?>