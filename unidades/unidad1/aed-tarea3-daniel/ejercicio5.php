<?php
/**
 * 5) Copiar contenido de un fichero a otro
 * Enunciado: Copia el contenido de origen.txt en un archivo copia.txt.
 * Funciones sugeridas: copy, file_get_contents, file_put_contents
 * @author danielrguezh
 * @version 1.0.0
 */
$origen = "resources/ejercicio5/original.txt";
$destino = "resources/ejercicio5/copia.txt";

if (!file_exists($origen)) {
    die("El archivo de origen no existe: $origen");
}

if (copy($origen, $destino)) {
    echo "Archivo copiado correctamente a $destino";
} else {
    echo "Error al copiar el archivo.";
}
?>