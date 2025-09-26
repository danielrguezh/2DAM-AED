<?php
/**
 * 
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
    return explode("  ",file_get_contents("resources/ejercicio8/tabla5.tx"));
}

$tablas=tablaGuardar(5);
foreach ($tablas as $key => $value) {
    echo "$value \n";
}
?>