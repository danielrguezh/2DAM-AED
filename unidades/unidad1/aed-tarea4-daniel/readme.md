## Ejercicio1: Operaciones con números naturales en PHP

### Enunciado

Dado un fichero `ops.csv` con columnas:

```code
z1,z2,op
```

donde:

- `op ∈ {suma, resta, producto, division}`  

Se debe calcular `z1 (op) z2` y generar como salida un fichero `resultado.csv`.

---

## Entrada (ejemplo)

Archivo: `ops.csv`

```csv
3,1,suma
5,2,producto
```

---

## Salida esperada

Archivo: `resultado.csv`

```csv
z1,z2,op,resultado
3,1,suma,4
5,2,producto,10
```

- `resultado` → número natural resultante de la operación.  
- Si ocurre una **división por 0**, escribir `ERROR` en la columna `resultado`.

---
```php
<?php
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
```

```text
rsultado.csv

z1,z2,op,resultado
3,1,resta,2
5,4,suma,9
5,2,producto,10
7,0,producto,0
10,3,division,3.3333333333333
8,0,division,ERROR

```

---

- **Entrada:** `ops.csv`
```
z1,z2,op
3,1,resta
5,4,suma
5,2,producto
7,0,producto
10,3,division
8,0,division
```

- **Salida:** `resultado.csv`  
```
z1,z2,op,resultado
3,1,resta,2
5,4,suma,9
5,2,producto,10
7,0,producto,0
10,3,division,3.3333333333333
8,0,division,ERROR

```
---

## Ejemplo de ejecución

```bash
php ejercicio1.php
```

### ops.csv

```code
z1,z2,op
3,1,suma
10,4,resta
2,8,resta
5,2,producto
7,0,producto
9,3,division
10,3,division
8,0,division
```

## Ejercicio 2: Estadísticas de palabras en PHP

### Enunciado

Dado un fichero `texto.txt`, contar cuántas palabras hay en total y cuántas veces aparece cada palabra.  

La salida se debe guardar en `estadisticas.csv` con el formato:

```code
palabra,frecuencia
```

### Reglas:

- Ignorar mayúsculas/minúsculas (ejemplo: `PHP` y `php` cuentan como la misma palabra).  
- Ignorar signos de puntuación.  

---

## Archivos de ejemplo

### Entrada (`texto.txt`)

```code
Zeus gobernaba desde el Olimpo, lanzando rayos y truenos. 
Atenea, diosa de la sabiduría, protegía a los héroes. 
Hércules realizaba sus doce trabajos, mientras Poseidón agitaba los mares con su tridente. 
Hades reinaba en el inframundo, y Afrodita inspiraba el amor entre los mortales.
```

### Salida esperada (`estadisticas.csv`)

```code
palabra,frecuencia
zeus,1
gobernaba,1
desde,1
el,3
olimpo,1
lanzando,1
rayos,1
y,2
truenos,1
atenea,1
diosa,1
de,1
la,1
sabiduría,1
protegía,1
a,1
los,3
héroes,1
hércules,1
realizaba,1
sus,1
doce,1
trabajos,1
mientras,1
poseidón,1
agitaba,1
mares,1
con,1
su,1
tridente,1
hades,1
reinaba,1
en,1
inframundo,1
afrodita,1
inspiraba,1
amor,1
entre,1
mortales,1
```

### Entrada (`texto.txt`)

```code
PHP es divertido. PHP es potente y divertido.
```

### Salida esperada (`estadisticas.csv`)

```code
palabra,frecuencia
php,2
es,2
divertido,2
potente,1
y,1
```

---
```php
<?php
 $contenido = file_get_contents("resources/ejercicio2/texto.txt");

function contarPalabras($contenido) : void {
    $contenido = strtolower($contenido);
    $contenido = preg_replace("/[[:punct:]]/u", " ", $contenido);

    $palabras = preg_split('/\s+/', $contenido, -1, PREG_SPLIT_NO_EMPTY);
    $frecuencias = array_count_values($palabras);

    $file = fopen("resources/ejercicio2/estadisticas.csv", "w");
    fwrite($file, "palabra,frecuencia\n");
    foreach ($frecuencias as $palabra => $frecuencia) {
        fwrite($file, "$palabra,$frecuencia\n");
    }
    fclose($file);
}

contarPalabras($contenido);
?>
```
