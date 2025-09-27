<?php
/**
 * Ejercicio1: Operaciones con números naturales en PHP
 * Dado un fichero ops.csv con columnas: z1,z2,op
 * donde: op ∈ {suma, resta, producto, division}
 * Se debe calcular z1 (op) z2 y generar como salida un fichero resultado.csv.
 * @author danielrguezh
 * @version 1.0.0
 */
$arrayArchivo = array_filter( explode("\n",file_get_contents("resources/ejercicio1/ops.csv")));


function escribir($arrayArchivo): void{
    $file = fopen("resources/ejercicio1/resultado.csv","w");
    fwrite($file,"z1,z2,op,resultado\n");
    for ($i=1; $i < sizeof($arrayArchivo) ; $i++) {
        $operacion = explode(",",$arrayArchivo[$i]);
        fwrite($file, trim($arrayArchivo[$i]).",".calcular(($operacion))."\n");
    }
}


function calcular($operacion): float|string{
    switch (strtolower(trim($operacion[2]))) {
        case 'suma':
            return $operacion[0] + $operacion[1];
        case 'resta':
            return $operacion[0] - $operacion[1];
        case 'producto':
            return $operacion[0] * $operacion[1];
        case 'division':
            if ($operacion[1] == 0) {
                return "ERROR";
            }
            return $operacion[0] / $operacion[1];
        default:
            return "Error desconocido\n";
    }
}

escribir($arrayArchivo);
?>