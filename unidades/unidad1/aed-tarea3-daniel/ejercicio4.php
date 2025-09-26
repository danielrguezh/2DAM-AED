<?php
/**
 * 4) Escribir y leer array en fichero
 * Enunciado: Guarda un array de nombres en nombres.txt (uno por línea).
 * Después, léelo y muéstralos en lista numerada.
 * Funciones sugeridas: fopen, fwrite, fgets
 * @author danielrguezh
 * @version 1.0.0
 */
function crearLeer(array $nombres=[]){
$file=fopen("resources/ejercicio4/nombres.txt","w+r");
foreach ($nombres as $key => $value) {
    fwrite($file,"$value \n");
}
$nombres=file_get_contents("resources/ejercicio4/nombres.tx");
return $nombres;
}
echo crearLeer(["Ana","Luis","Marta","Carlos","Julia"]);

?>