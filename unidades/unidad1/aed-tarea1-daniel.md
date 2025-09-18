# Creación y uso de elementos básicos en php

## Variables y Condicionales
### 1.Mayor de dos números
Pide dos números y muestra cuál es mayor o si son iguales.
```php
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

```php
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
```php
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
```php
<?php
$nota = 10;
echo "$nota<br>";

$mensaje = "La nota obtenida es incorrecta";

switch ($nota) {
    case ($nota < 5):
        $mensaje = "Suspenso";
        break;
    case ($nota <= 6):
        $mensaje = "Aprobado";
        break;
    case ($nota <= 8):
        $mensaje = "Notable";
        break;
    case ($nota <= 10):
       $mensaje = "Sobresaliente";
        break;        
}

echo "$mensaje";

?>
```

##  Bucles (for, while, foreach)
### 5.Contar del 1 al 100
Muestra los números del 1 al 100 en pantalla.
```php
<?php

for ($i = 1; $i <= 100; $i++) {
    echo $i;
    echo '<br>';
}

?>
```

### 6.Suma acumulada
Calcula la suma de los números del 1 al 50 usando un bucle.
```php
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
```php
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
```php
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
```php
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
```php
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
```php
<?php

for ($i = 2; $i <= 50; $i++) {
    $esPrimo = true;
    for ($j = 2; $j < $i; $j++) {
        if ($i % $j == 0) {
            $esPrimo = false;
            break;
        }
    }
    if ($esPrimo) {
        echo $i . "\n";
    }
}
?>
```

### 12.Fibonacci
Calcula el factorial de un número introducido (ejemplo: 5! = 120).
```php
<?php
$primero = 0;
$segundo = 1;

echo "$primero <br> $segundo <br>";

for ($i = 3; $i <= 20; $i++) {
    $siguiente = $primero + $segundo;
    echo "$siguiente <br>";
    $primero = $segundo;
    $segundo = $siguiente;
}
?> 
```

### 13.Múltiplos de un número
Pide un número n y muestra sus múltiplos hasta 100.
```php
<?php

$numero = 7;

for ($i = $numero; $i <= 100; $i += $numero) {
    echo $i . " ";
}

?>
```

### 14.Suma de pares e impares
Calcula la suma de los números pares e impares entre 1 y 100 por separado.
```php
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
```php
<?php

$numero = rand(1,20);
$desconocido = true;
while ($adivinado) {
    $respuesta = readline("ingresa un numero ");
    if ($respuesta < $numero) {
        echo "el numero ingresado es menor <br>";
    } else if($respuesta > $numero){
        echo "el numero ingresado es mayor <br>";
    }
    if ($respuesta == $numero) {
        $desconocido = false;
        break;
    }
}
echo "adivinaste";

?>
```

## Construcción de Algorítmicos
### 16.Número perfecto
Comprueba si un número es perfecto (la suma de sus divisores propios es igual al número).
```php
<?php

$numero = 6;
$sumaDivisores = 0;

$mensaje = "$numero no es un numero perfecto.";

for ($i = 1; $i <= $numero / 2; $i++) {
	if ($numero % $i == 0) {
		$sumaDivisores += $i;
	}
}

if ($sumaDivisores == $numero) {
	$mensaje =  "$numero es un número perfecto.";
} 
echo $mensaje;

?>
```

### 17.Invertir número
Escribe un algoritmo que invierta los dígitos de un número (ejemplo: 123 → 321).
```php
<?php

 $numero = 123; 
 $invertido = 0;
echo "$numero <br>";
while ($numero > 0) {
	$digito = $numero % 10;
	$invertido= $invertido * 10 + $digito;
	$numero = intdiv($numero, 10);
}
echo "$invertido";

?>
```

### 18.Palíndromo
Comprueba si una palabra almacenada en una variable es palíndroma.
```php
<?php
$palabra = "hola";

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
```php
<?php

$a = 56;
$b = 98;
echo "$a <br> $b <br>";
while ($b != 0) {
	$temp = $b;
	$b = $a % $b;
	$a = $temp;
}

echo "El MCD es $a";

?>
```

### 20.PTriángulo de asteriscos
Muestra en pantalla un triángulo de altura n usando *. \
Ejemplo con n = 5:

    *
    **
    ***
    ****
    *****

```php
<?php
$numero = 5;
for ($i = 1; $i <= $numero; $i++) {
    $line = "";
    for ($j = 1; $j <= $i; $j++) {
        $line .= "*";
    }
    echo $line . "<br>";
}
?> 
```