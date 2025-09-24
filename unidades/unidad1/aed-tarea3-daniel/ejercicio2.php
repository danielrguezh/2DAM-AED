<?php
/**
 * 2) Guardar números en un fichero
 * Enunciado: Almacena en numeros.txt los números del 1 al 10 
 * (cada número en una línea). Luego léelo y muestra todos los números.
 * Funciones sugeridas: fopen, fwrite, fclose, file
 * @author danielrguezh
 * @version 1.0.0
 */
    //try{
        $file = fopen("resources/numeros.txt", "w");
        if(!$file){
            throw new Exception("Archivo no encontrado.");
        }
    //}
?>