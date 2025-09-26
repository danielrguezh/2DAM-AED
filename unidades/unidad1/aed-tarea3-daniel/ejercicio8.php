<?php
/**
 * 8) Crear fichero de multiplicaciones
 * Enunciado: Genera la tabla del 5 y guárdala en tabla5.txt. Luego muéstrala.
 * Funciones sugeridas: fopen, fwrite, file
 * @author danielrguezh
 * @version 1.0.0
 */
function tablaGuardar(int $n){
   $file= fopen("resources/ejercicio8/tabla5.txt","r+w");
    for ($i=1; $i <11 ; $i++) { 
        $multiplicacion=$n*$i;
       fwrite($file,"$n x $i = $multiplicacion  \n");
    }
    fclose($file);
    return explode("  ",file_get_contents("resources/ejercicio8/tabla5.txt"));
}

$tablas=tablaGuardar(5);
foreach ($tablas as $key => $value) {
    echo "$value \n";
}
?>