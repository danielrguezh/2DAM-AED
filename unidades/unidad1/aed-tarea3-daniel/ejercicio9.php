<?php
/**
 * 9) Registrar entradas de usuario
 * Enunciado: Guarda los nombres recibidos en un formulario dentro de usuarios.txt
 * (cada nombre en una línea). Luego muéstralos.
 * Funciones sugeridas: fopen, fwrite, file
 * @author danielrguezh
 * @version 1.0.0
 */
    function guardarLeerUsuarios(array $usuarios): string {
        $file = fopen("resources/ejercicio9/usuarios.txt", "w+r");
        foreach ($usuarios as $usuario) { 
            fwrite($file, "$usuario\n");
        }
        $usuarios = file_get_contents("resources/ejercicio9/usuarios.txt");
        return $usuarios;
    }

    $usuarios = ["Ana","Pedro","Lucía"];
    echo guardarLeerUsuarios($usuarios);

?>