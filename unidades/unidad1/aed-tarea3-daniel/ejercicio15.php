<?php
/**
 * 15) Lista de chistes (rotativos)
 * Enunciado: Muestra un chiste distinto en cada ejecución desde chistes.txt.
 * @author danielrguezh
 * @version 1.0.0
 */

$content = explode("\n", file_get_contents("resources/ejercicio15/chistes.txt"));

function chisteRandom(): string {
    global $content;

    if (empty($content)) {
        return null;
    }

    $index = array_rand($content);
    $chiste = $content[$index];
    unset($content[$index]);
    return $chiste;
}
for ($i=0; $i=sizeof($content);$i++){
    echo chisteRandom() . "\n";
}
?>
