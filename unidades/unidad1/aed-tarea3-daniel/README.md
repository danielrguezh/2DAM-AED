# <img src=../../../../../images/computer.png width="40"> Code, Learn & Practice(Acceso a Datos: "Ficheros")

## ✅ Preparación

```bash
php -v
```

Ejecuta:

```bash
php ejercicioX.php
```

Crea un fichero por ejercicio: `ejercicio01.php` … `ejercicio30.php`.

---

### 1) Hola fichero (crear y leer)

**Enunciado:** Crea un fichero `datos.txt` con el texto *"Hola Mundo desde PHP"* y, a continuación, léelo y muestra su contenido por pantalla.  
**Funciones/Comandos sugeridos:** `file_put_contents`, `file_get_contents`  

**Ejemplo de fichero generado:**

```code
datos.txt
Hola Mundo desde PHP
```

---
```php
?php
file_put_contents("resources/ejercicio1/datos.txt", "Hola Mundo desde PHP");
echo file_get_contents("resources/ejercicio1/datos.txt");
?>
```

```
Hola Mundo desde PHP
```
---

### 2) Guardar números en un fichero

**Enunciado:** Almacena en `numeros.txt` los números del 1 al 10 (cada número en una línea). Luego léelo y muestra todos los números.  
**Funciones sugeridas:** `fopen`, `fwrite`, `fclose`, `file`  

**Ejemplo de fichero generado:**

```code
numeros.txt
1
2
3
...
10
```

---
```php
<?php

$file=fopen("resources/ejercicio2/numeros.txt","w");
for ($i=1; $i < 11; $i++) { 
   fwrite($file,"$i\n");
 
}
fclose($file);
?>
```

```
numeros.txt
1
2
3
4
5
6
7
8
9
10
```
---
### 3) Contar palabras en un fichero

**Enunciado:** Escribe un texto en `texto.txt`, luego haz que tu programa cuente cuántas palabras contiene ese archivo.  
**Funciones sugeridas:** `file_get_contents`, `str_word_count`  

**Ejemplo de fichero generado:**

```code
texto.txt
PHP es muy divertido y potente.
```

---
```php
<?php
$file="resources/ejercicio3/texto.txt";
$datos=file_get_contents($file);
echo str_word_count($datos);
?>
```

```
6
```
---

### 4) Escribir y leer array en fichero

**Enunciado:** Guarda un array de nombres en `nombres.txt` (uno por línea). Después, léelo y muéstralos en lista numerada.  
**Funciones sugeridas:** `fopen`, `fwrite`, `fgets`  

**Ejemplo de fichero generado:**

```code
nombres.txt
Ana
Luis
Marta
Carlos
Julia
```

---
```php
<?php
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
```

```
Ana
Luis
Marta
Carlos
Julia
```
---

### 5) Copiar contenido de un fichero a otro

**Enunciado:** Copia el contenido de `origen.txt` en un archivo `copia.txt`.  
**Funciones sugeridas:** `copy`, `file_get_contents`, `file_put_contents`  

**Ejemplo de fichero inicial:**

```code
origen.txt
Este es el archivo original.
```

**Ejemplo de fichero resultante:**

```code
copia.txt
Este es el archivo original.
```

---
```php
<?php
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
```

```
Archivo copiado correctamente a resources/ejercicio5/copia.txt
```
---

### 6) Invertir el contenido de un fichero

**Enunciado:** Lee `frase.txt`, invierte el texto y guarda el resultado en `frase_invertida.txt`.  
**Funciones sugeridas:** `file_get_contents`, `strrev`, `file_put_contents`  

**Ejemplo de fichero inicial:**

```code
frase.txt
Hola PHP
```

**Ejemplo de fichero resultante:**

```code
frase_invertida.txt
PHP aloH
```

---
```php
<?php
$file="resources/ejercicio6/frase.txt";
$fileReverse="resources/ejercicio6/frase_invertida.txt";
$contenido=file_get_contents($file);
file_put_contents($fileReverse,strrev($contenido));
?>
```

```
frase_invertida.txt
PHP aloH
```
---

### 7) Calcular suma desde fichero

**Enunciado:** Guarda números separados por comas en `datos_numericos.txt`. Léelos y calcula su suma.  
**Funciones sugeridas:** `file_get_contents`, `explode`, `array_sum`  

**Ejemplo de fichero:**

```code
datos_numericos.txt
10,20,30,40
```

---
```php
<?php
$file= "resources/ejercicio7/datos_numericos.txt";
$contenido=file_get_contents($file);
echo $contenido;
$contenido=explode(",",$contenido);
     
echo "\n" . array_sum($contenido);
?>
```

```
10,20,30,40
100
```
---

### 8) Crear fichero de multiplicaciones

**Enunciado:** Genera la tabla del 5 y guárdala en `tabla5.txt`. Luego muéstrala.  
**Funciones sugeridas:** `fopen`, `fwrite`, `file`  

**Ejemplo de fichero:**

```code
tabla5.txt
5 x 1 = 5
5 x 2 = 10
...
5 x 10 = 50
```

---
```php
<?php
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
```

```
5 x 1 = 5

5 x 2 = 10

5 x 3 = 15

5 x 4 = 20

5 x 5 = 25

5 x 6 = 30

5 x 7 = 35

5 x 8 = 40

5 x 9 = 45

5 x 10 = 50
```
---

### 9) Registrar entradas de usuario

**Enunciado:** Guarda los nombres recibidos en un formulario dentro de `usuarios.txt` (cada nombre en una línea). Luego muéstralos.  
**Funciones sugeridas:** `fopen`, `fwrite`, `file`  

**Ejemplo de fichero:**

```code
usuarios.txt
Ana
Pedro
Lucía
```

---
```php
<?php

    function guardarLeerUsuarios(array $usuarios): string {
        $file = fopen("resources/ejercicio9/usuarios.txt", "w+r");
        foreach ($usuarios as $usuario) { 
            fwrite($file, "$usuario\n");
        }
        $usuarios = file_get_contents("resources/ejercicio9/usuarios.txt");
        return $usuarios;
    }

    $usuarios = ["Ana","Pedro","Lucía"];
    echo guardarLeerUsuarios($usuarios);

?>
```

```
Ana
Pedro
Lucía
```
---

### 10) Leer JSON desde fichero

**Enunciado:** Crea `datos.json` con información de personas (nombre y edad). Haz que el programa lo lea y muestre los datos.  
**Funciones sugeridas:** `file_get_contents`, `json_decode`  

**Ejemplo de fichero:**

```code
datos.json
[
  {"nombre": "Ana", "edad": 25},
  {"nombre": "Luis", "edad": 30}
]
```

---
```php
<?php
function leerJSON(){
    $rutaArchivo = "resources/ejercicio10/datos.json";
    $archivo = fopen($rutaArchivo, "r");

    $datosJson = file_get_contents($rutaArchivo);
    $datos = json_decode($datosJson);
    fclose($archivo);
    print_r(value: $datos);
}

leerJSON();
?>
```

```
Array
(
    [0] => stdClass Object
        (
            [nombre] => Ana
            [edad] => 25
        )

    [1] => stdClass Object
        (
            [nombre] => Luis
            [edad] => 30
        )

)
```
---

### 11) Diario personal secreto

**Enunciado:** Guarda entradas con fecha y hora en `diario.txt`. Luego muéstralas todas.  

**Ejemplo de fichero:**

```code
diario.txt
[2025-09-24 10:00] Hoy aprendí PHP con ficheros 😄
[2025-09-24 12:00] Almorcé pizza mientras programaba.
```

---
```php
<?php

 function diario($entrada){
   $rutaArchivo = "resources/ejercicio11/diario.txt";
   $archivo = fopen($rutaArchivo, "a");

   $fechaActual = date('[Y-m-d H:i:s]');
   $entradaFormato = "$fechaActual $entrada \n";

   fwrite($archivo, "$entradaFormato");

   echo file_get_contents($rutaArchivo);
   fclose($archivo);
}
diario("Hola, estoy programando");
?>
```

```
[2025-09-26 13:45:41] Hola, estoy programando
[2025-09-26 13:45:43] Hola, estoy programando
[2025-09-26 22:19:35] Hola, estoy programando
```
---

### 12) Ranking de videojuegos

**Enunciado:** Guarda juegos con puntuaciones en `ranking.txt`, ordénalos y muestra el top 3.  

**Ejemplo de fichero:**

```code
ranking.txt
Zelda: 10
Mario: 9
Sonic: 8
```

---
```php
<?php
function guardarPuntuacion(array $juegos): void {
    $path = "resources/ejercicio12/ranking.txt";
    $file = fopen($path, "w");
    foreach ($juegos as $juego => $puntuacion) {
        fwrite($file, "$juego: $puntuacion\n");
    }
    fclose($file);
} 

function leer3Mejores(): void {
    $path = "resources/ejercicio12/ranking.txt";
    $lineas = file($path);

    $ranking = [];
    foreach ($lineas as $linea) {
        [$juego, $puntos] = explode(":", $linea);
        $ranking[trim($juego)] = (int) trim($puntos);
    }

    arsort($ranking);
    $top = array_slice($ranking, 0, 3);
    $posicion = 1;
    foreach ($top as $juego => $puntos) {
        echo "$posicion. $juego ($puntos puntos)\n";
        $posicion++;
    }
}

$juegos = [
    "Zelda" => 10, 
    "Sonic" => 8,
    "Mario" => 9,
    "For Honor" => 9
];

guardarPuntuacion($juegos);
leer3Mejores();

?>
```

```
1. Zelda (10 puntos)
2. Mario (9 puntos)
3. For Honor (9 puntos)
```

---

### 13) Canción aleatoria

**Enunciado:** Guarda títulos en `canciones.txt` y muestra uno al azar.  

**Ejemplo de fichero:**

```code
canciones.txt
Hysteria
Bohemian Rhapsody
Africa
```

---
```php
<?php

function guardarCanciones(array $canciones): void {
    $file = fopen("resources/ejercicio13/canciones.txt", "w");
    foreach ($canciones as $cancion) {
        fwrite($file, "$cancion\n");
    }
}

function obtenerCancionRandom(): string {
    $file = file_get_contents("resources/ejercicio13/canciones.txt");
    $canciones = explode("\n", $file);
    $index = array_rand($canciones);
    return $canciones[$index];
}

$canciones = ["Hysteria", "Bohemian Rhapsody", "Africa"];
guardarCanciones($canciones);
echo obtenerCancionRandom();
?>
```

```
Africa
```
---

### 14) Generador de excusas divertidas

**Enunciado:** Lee excusas desde `excusas.txt` y muestra una aleatoria.  

**Ejemplo de fichero:**

```code
excusas.txt
Mi perro se comió la tarea.
El Wi-Fi decidió tomarse el día libre.
Me abdujeron los marcianos.
```

---
```php
<?php
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
```

```
Me abdujeron los murcianos.
```
---

### 15) Lista de chistes (rotativos)

**Enunciado:** Muestra un chiste distinto en cada ejecución desde `chistes.txt`.  

**Ejemplo de fichero:**

```code
chistes.txt
¿Sabes cuál es el colmo de un programador? Tener mala RAM-oria.
Ayer vi un bit triste… estaba des-bit-ido.
Yo no sudo, compilo.
```

---
```php
<?php
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
```

```
Yo no sudo, compilo.
¿Sabes cuál es el colmo de un programador? Tener mala RAM-oria.
Van dos en una moto y se cae el del medio.
```
---

### 16) Adivina la palabra

**Enunciado:** Elige una palabra de `palabras.txt` y muestra solo 2 letras, el usuario debe adivinarla.  

**Ejemplo de fichero:**

```code
palabras.txt
manzana
platano
cereza
```

---
```php
<?php
    $palabras = file("resources/ejercicio16/palabras.txt");
    $random = array_rand($palabras);
    $adivina = $palabras[$random];

    $adivinaArray = str_split($adivina);
    $tamanio = sizeof($adivinaArray);

    $pos1 = rand(0, $tamanio - 1);
    do {
        $pos2 = rand(0, $tamanio - 1);
    } while ($pos2 == $pos1);

    $oculta = "";
    for ($i = 0; $i < $tamanio-1; $i++) {
        if ($i == $pos1 || $i == $pos2) {
            $oculta .= $adivinaArray[$i];
        } else {
            $oculta .= "_";
        }
    }

    $intento = readline("Adivina la palabra: $oculta\n ");
    $input = trim($intento);

    if (strtolower($input) == trim(strtolower($adivina))) {
        echo "Correcto\n";
    } else {
        echo "Incorrecto. La palabra era: $adivina\n";
    }
?>
```

```
Adivina la palabra: _a_____ lala
Incorrecto. La palabra era: manzana
```
---

### 17) Generador de nombres para superhéroes

**Enunciado:** Combina palabras de `adjetivos.txt` y `animales.txt`.  

**Ejemplo de ficheros:**

```code
adjetivos.txt
Increíble
Rápido
Misterioso
```

```code
animales.txt
Tigre
Águila
Lobo
```

---
```php
<?php
$adjetivos = file("resources/ejercicio17/adjetivos.txt");
$animales = file("resources/ejercicio17/animales.txt");

$adj = $adjetivos[array_rand($adjetivos)];
$animal = $animales[array_rand($animales)];

$combinacion = trim($adj) . " " . trim($animal);
echo "$combinacion\n";
?>
```

```
Rápido Lobo
```
---
### 18) Encuesta de comidas favoritas

**Enunciado:** Guarda respuestas de usuarios en `comidas.txt` y genera ranking.  

**Ejemplo de fichero:**

```code
comidas.txt
pizza
sushi
pizza
pasta
```

---
```php
<?php
$archivo = "resources/ejercicio18/comidas.txt";

function agregarComida(string $archivo, string $comida): void {
    $comida = trim($comida);
    if ($comida === "") return;
    file_put_contents($archivo, $comida . PHP_EOL, FILE_APPEND);
}

function mostrarRankingDesdeArchivo(string $archivo): void {
    if (!file_exists($archivo)) {
        echo "El archivo no existe.\n";
        return;
    }

    $lineas = file($archivo);
    $ranking = [];
    foreach ($lineas as $linea) {
        $comida = trim($linea);
        if ($comida === "") continue;
        if (isset($ranking[$comida])) {
            $ranking[$comida]++;
        } else {
            $ranking[$comida] = 1;
        }
    }

    arsort($ranking);
    $posicion = 1;
    foreach ($ranking as $comida => $veces) {
        echo "$posicion. $comida: $veces\n";
        $posicion++;
    }
}

$comida = readline("Agregue su comida favorita: ");
agregarComida($archivo, $comida);
mostrarRankingDesdeArchivo($archivo);

?>
```

```
Agregue su comida favorita: arepa
1. arepa: 3
2. pizza: 1
3. sushi: 1
4. sopa: 1
```
---

### 19) Simulador de tweets

**Enunciado:** Guarda tweets en `tweets.txt` con fecha y hora, muestra los últimos 5.  

**Ejemplo de fichero:**

```code
tweets.txt
[2025-09-24 09:30] Aprendiendo PHP con ejercicios divertidos #php
[2025-09-24 10:00] Otro día más programando 🚀
```

---
```php
<?php 

function crearTwitt($entrada){
   $rutaArchivo = "resources/ejercicio19/tweets.txt";
   $archivo = fopen($rutaArchivo, "a");

   $fechaActual = date('[Y-m-d H:i:s]');
   $entradaFormato = "$fechaActual $entrada\n";

   fwrite($archivo, $entradaFormato);
   fclose($archivo);

}

function mostrarUltimosTwitts($n = 5){
    $contenido = file_get_contents("resources/ejercicio19/tweets.txt");
    $twitts = array_filter(explode("\n", $contenido));

    
    $ultimos = array_slice($twitts, -$n);

    echo " Últimos $n twitts:\n";
    foreach (array_reverse($ultimos) as $twitt) {
        echo $twitt . "\n";
    }
}

crearTwitt("Hola, estoy programando otra vez");
mostrarUltimosTwitts();
?>
```

```
 Últimos 5 twitts:
[2025-09-26 22:26:13] Hola, estoy programando otra vez
[2025-09-26 20:15:55] Hola, estoy programando otra vez
[2025-09-26 20:07:49] Hola, estoy programando otra vez
[2025-09-26 20:07:48] Hola, estoy programando otra vez
[2025-09-26 20:07:48] Hola, estoy programando otra vez
```
---

### 20) Historias locas (Mad Libs)

**Enunciado:** Reemplaza placeholders en `plantilla.txt` con palabras aleatorias de otros ficheros.  

**Ejemplo de ficheros:**

```code
plantilla.txt
Un [animal] viajó a [lugar] para comer [comida].
```

```code
animales.txt
gato
dragón
perro
```

```code
lugares.txt
la Luna
Tokio
la playa
```

```code
comidas.txt
tacos
ramen
helado
```
---
```php
<?php
$plantilla = file_get_contents("resources/ejercicio20/plantilla.txt");

$animales = explode("\n",file_get_contents("resources/ejercicio20/animales.txt"));
$lugares  = explode("\n",file_get_contents("resources/ejercicio20/lugares.txt"));
$comidas  = explode("\n",file_get_contents("resources/ejercicio20/comidas.txt"));

$plantilla = str_replace("[animal]", $animales[array_rand($animales)], $plantilla);
$plantilla = str_replace("[lugar]", $lugares[array_rand($lugares)], $plantilla);
$plantilla = str_replace("[comida]", $comidas[array_rand($comidas)], $plantilla);

echo $plantilla;
?>
```

```
Un dragón viajó a Tokio para comer helado.
```
---
## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md]([../../../LICENSE.md](https://github.com/jpexposito/code-learn-practice/blob/main/LICENSE)) para detalles.
