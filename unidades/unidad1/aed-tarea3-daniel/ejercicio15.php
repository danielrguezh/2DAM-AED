<?php
/**
 * 15) Lista de chistes (rotativos)
 * Enunciado: Muestra un chiste distinto en cada ejecución desde chistes.txt.
 * @author danielrguezh
 * @version 1.0.0
 */


$content=explode("\n",file_get_contents("resources/ejercicio15/chistes.txt"));

function chisteRandom(): string{
    while(sizeof($content)>0){
        shuffle($content);
        $random=$content[sizeof($content)-1];
        array_pop($content);
        return $random;
    }
    
}

echo chisteRandom();
echo "\n";
echo chisteRandom();
echo "\n";
echo chisteRandom();
?>
