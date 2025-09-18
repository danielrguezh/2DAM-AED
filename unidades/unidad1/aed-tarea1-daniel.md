# Creación y uso de elementos básicos en php

## Variables y Condicionales
### 1.Mayor de dos números
Pide dos números y muestra cuál es mayor o si son iguales.
```
<?php
$numero1 = 10;
$numero2 = 3;
echo "$numero1<br>";
echo "$numero2<br>";

$mensaje = "Ambos numeros son iguales";

if ($numero1 > $numero2) {
    $mensaje = "$numero1 es el mayor";
} 
if ($numero1 < $numero2) {
    $mensaje = "$numero2 es el mayor";
}

echo "$mensaje";

?>
```

### 2.Edad permitida
Pide la edad de una persona y muestra:
* Eres menor de edad" si es < 18.
* Eres mayor de edad" si es ≥ 18.

```
<?php
$edad = 10;
echo "$edad<br>";

$mensaje = "Eres mayor de edad";

if ($numero1 < 18) {
    $mensaje = "Eres menor de edad";
}

echo "$mensaje";

?>
```

### 3.Positivo, negativo o cero
Comprueba si un número almacenado en una variable es positivo, negativo o cero.
```
<?php
$numero = 10;
echo "$numero<br>";

$mensaje = "El numero es 0";

if ($numero > 0) {
    $mensaje = "El numero es positivo";
} 
if ($numero < 0) {
    $mensaje = "El numero es negativo";
}

echo "$mensaje";

?>
```

### 4.Nota final
Pide la nota de un alumno y muestra:
* "Suspenso" (< 5), "Aprobado" (5–6), "Notable" (7–8), "Sobresaliente" (9–10).
```
<?php
$nota = 10;
echo "$nota<br>";

$mensaje = "La nota obtenida es incorrecta";

switch ($nota) {
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
        $mensaje = "Suspenso";
        break;
    case 5:
    case 6:
        $mensaje = "Aprobado";
        break;
    case 7:
    case 8:
        $mensaje = "Notable";
        break;
    case 9:
    case 10:
       $mensaje = "Sobresaliente";
        break;        
}

echo "$mensaje";

?>
```

##  Bucles (for, while, foreach)
### 5.Contar del 1 al 100
Muestra los números del 1 al 100 en pantalla.
```
<?php

for ($i = 1; $i <= 100; $i++) {
    echo $i;
    echo '<br>';
}

?>
```

### 6.Suma acumulada
Calcula la suma de los números del 1 al 50 usando un bucle.
```
<?php

$numero = 1;
echo $numero;
echo '<br>';

for ($i = 2; $i <= 50; $i++) {
	$numero += $i;
    echo $numero;
    echo '<br>';
}

?>
```

### 7.Tabla de multiplicar
Pide un número y genera su tabla de multiplicar del 1 al 10.
```
<?php

$numero = 7;

for ($i = 1; $i <= 10; $i++) {
	$resultado = $numero*$i;
    echo $resultado;
    echo '<br>';
}

?>
```

### 8.Números pares
Muestra todos los números pares entre 1 y 50.
```
<?php
for ($i = 1; $i <= 50; $i++) {
	if ($i % 2== 0){
    	echo $i;
    	echo '<br>';
    }
}

?>
```

### 9.Cuenta atrás
Haz un bucle que cuente de 10 a 1 y luego muestre "¡Fin!".
```
<?php

$contador=10;
while($contador > 0) {
	echo "$contador <br>";
    $contador--;
}
echo "¡Fin!";

?>
```

### 10.Factorial
Calcula el factorial de un número introducido (ejemplo: 5! = 120).
```
<?php

$numero = 5;
$factorial = 1;

for ($i = 1; $i <= $numero; $i++) {
    $factorial *= $i;
}

echo "El factorial de $numero es: $factorial";

?>
```

## Combinando Condicionales y Bucles
### 11.Números primos
Escribe un algoritmo que muestre los números primos entre 1 y 50.
```

```

### 12.Fibonacci
Calcula el factorial de un número introducido (ejemplo: 5! = 120).
```

```

### 13.Múltiplos de un número
Pide un número n y muestra sus múltiplos hasta 100.
```
<?php

$n = 7;

for ($i = $n; $i <= 100; $i += $n) {
    echo $i . " ";
}

?>
```

### 14.Suma de pares e impares
Calcula la suma de los números pares e impares entre 1 y 100 por separado.
```
<?php

$sumaPares = 0;
$sumaImpares = 0;

for ($i = 1; $i <= 100; $i++) {
	if ($i % 2 == 0) {
    	$sumaPares += $i;
    } else {
    	$sumaImpares += $i;
    }
    
}

echo "Suma de pares = $sumaPares ";
echo "Suma de impares = $sumaImpares";

?>
```

### 15.Adivinar número
Genera un número aleatorio entre 1 y 20. \
Pide al usuario que lo adivine y usa un bucle con condicionales para dar pistas: "Mayor" o "Menor".

```

```

## Construcción de Algorítmicos
### 16.Número perfecto
Comprueba si un número es perfecto (la suma de sus divisores propios es igual al número).

```

```

### 17.Invertir número
Escribe un algoritmo que invierta los dígitos de un número (ejemplo: 123 → 321).

```

```

### 18.Palíndromo
Comprueba si una palabra almacenada en una variable es palíndroma.
```
<?php
$palabra = "hola";
n 
$palabraMinuscula = strtolower($palabra);

if ($palabraMinuscula === strrev($palabraMinuscula)) {
    echo "'$palabra' es un palindromo";
} else {
    echo "'$palabra' no es un palindromo";
}
?>
```

### 19.Máximo común divisor (MCD)
Escribe un algoritmo que calcule el MCD de dos números.
```

```

### 20.PTriángulo de asteriscos
Muestra en pantalla un triángulo de altura n usando *. \
Ejemplo con n = 5:
```
*
**
***
****
*****
```

```
<?php
$n = 5; // altura del triángulo

for ($i = 1; $i <= $n; $i++) {
    // imprimimos $i asteriscos
    echo str_repeat("*", $i) . "<br>";
}
?>
```