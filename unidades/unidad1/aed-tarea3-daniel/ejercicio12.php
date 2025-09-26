<?php
/**
 * 12) Ranking de videojuegos
 * Enunciado: Guarda juegos con puntuaciones en ranking.txt,
 * ordénalos y muestra el top 3.
 * @author danielrguezh
 * @version 1.0.0
 */
function guardar(String $nombre,int $ranking){
    $content=explode("\n",file_get_contents("resources/ejercicio12/ranking.txt"));
    $listContent=[];
    foreach ($content as $key => $value) {
        $listContent[]=explode(":",$value);
    }
    $listContent[]=[$nombre," $ranking"];
    $file=fopen("ranking.txt","w");
    foreach ($listContent as $key => $value) {
        for ($i=0; $i < 2; $i++) { 
            if($i%2==0){
                $value[$i]="$value[$i]:";
            }
             fwrite($file,$value[$i]);
        }
           fwrite($file,"\n");
    }
    fclose($file);
}
    guardar("Fortnite",5);
    guardar("The Witcher",7);
    guardar("For honor",8);
?>