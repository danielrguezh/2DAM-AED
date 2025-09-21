# Creación y uso de elementos funciones en php
## Número capicúa (palíndromo numérico)

Implementa una función __esCapicua(int $n): bool__ que determine si un número entero positivo es capicúa.

- Un número es capicúa si se lee igual de izquierda a derecha que de derecha a izquierda.

> Ejemplo: `12321` → `true`

---
```php
<?php
function esCapicua(int $numero): bool {
    $invertido=0;
    while ($numero > 0) {
	$digito = $numero % 10;
	$invertido= $invertido * 10 + $digito;
	$numero = intdiv($numero, 10);
    }
    return ($numero === $invertido);
}

$numero = 12321;
echo esCapicua($numero);
?>
```

## Escalera de asteriscos

Implementa una función __montañaAsteriscos(int $n, $m): void__ que imprima una escalera de asteriscos de altura `N`y el tamaño M.

- La primera fila contiene 1 asterisco, la segunda 2, y así hasta `N`, `M` veces.

> Ejemplo con entrada `4,2`:

```text
*.     *
**.   **
***  ***
********
```
---
```php
<?php
function montaniaAsteriscos(int $n, int $m): array {
    $resultado = [];

    for ($i = 1; $i <= $n; $i++) {
        $line = "";
        $spaces = str_repeat(" ", $n - $i);
        $stars  = str_repeat("*", $i);

        for ($j = 0; $j < $m; $j++) {
            if ($j % 2 != 0) {
                // alineado a la derecha
                $line .= $spaces . $stars;
            } else {
                //  alineado a la izquierda
                $line .= $stars . $spaces;
            }
        }

        $resultado[] = $line;
    }

    return $resultado;
}

$montanias = montaniaAsteriscos(4, 4);

echo "<pre>" . implode("\n", $montanias) . "</pre>";
?>
```
## Suma de dígitos

Implementa una función __sumaDigitos(int $n): int__ que calcule la suma de los dígitos de un número entero positivo.

- Descompón el número en dígitos y súmalos.

> Ejemplo: `2025` → `9` (2+0+2+5)
---
```php
<?php
function sumaDigitos(int $n): int {
    $suma=0;
    while ($n > 0) {
	$digito = $n % 10;
	$suma += $digito;
	$n = intdiv($n, 10);
    }
    return $suma;
}

$numero = 2025;
echo sumaDigitos($numero);
?>
```
## Número secreto (múltiplos de 3 o 5)

Implementa una función __multiplosTresOCinco(int $n): array__ que devuelva todos los múltiplos de 3 o 5 menores que `N`.

- Además, calcula la suma de dichos múltiplos.

> Ejemplo con entrada `10`:

```code
3, 5, 6, 9
Suma = 23
```
---
```php
<?php
function multiplosTresOCinco(int $n): array {
    $multiplos=[];
    for($i=1;$i<$n;$i++){
        if($i%3==0  || $i%5==0){
            array_push($multiplos, $i);
        }
    }
    return $multiplos;
}

$numero = 10;
$numeros=multiplosTresOCinco($numero);
$suma=0;
foreach($numeros as $numero){
    echo $numero . " ";
    $suma+=$numero;
}
echo "<br> Suma = $suma";
?>
```
## Secuencia de Collatz

Implementa una función __secuenciaCollatz(int $n): array__ que genere la secuencia de Collatz a partir de un entero positivo.

- Si el número es par → dividir entre 2.  
- Si es impar → multiplicar por 3 y sumar 1.  
- Repetir hasta llegar a 1.

> Ejemplo con entrada `6`:

```code
6 → 3 → 10 → 5 → 16 → 8 → 4 → 2 → 1
```
---
```php
<?php
function secuenciaCollatz(int $n): array {
    $secuencia=[];
    array_push($secuencia, $n);
    while($n != 1){
        if($n%2==0){
        	$n = intdiv($n,2);
            array_push($secuencia, $n);
        } else {
        	$n = $n * 3 + 1;
            array_push($secuencia, $n);
        }
    }
    return $secuencia;
}

$numero = 6;
$numeros=secuenciaCollatz($numero);
foreach($numeros as $numero){
    echo $numero . " ";
}
?> 
```
