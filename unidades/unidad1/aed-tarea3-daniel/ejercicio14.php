<?php
/**
 * 14) Generador de excusas divertidas
 * Enunciado: Lee excusas desde excusas.txt y muestra una aleatoria.
 * @author danielrguezh
 * @version 1.0.0
 */
function excusas(): array{
    $content=explode("\n",file_get_contents("resources/ejercicio14/excusas.txt"));
    return $content;
}

function excusasRandomSelect(): string{
    $content=excusas();
    $index=array_rand($content);
    return $content[$index];
}
echo excusasRandomSelect();
?>