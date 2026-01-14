/**
 * E09 – Async/Promises
 * @author danielrguezh
 * @version 1.0.0
 */

export async function delay(ms: number): Promise<void> {
  // resuelve tras ms; Error si ms<0 o no finito
  
  if(!Number.isFinite(ms) ||  ms<0){
     throw new Error();
  }

  return new Promise(response => {
    setTimeout(response, ms);
  });
}

export async function retry<T>(fn: () => Promise<T>, attempts: number): Promise<T> {
  // reintenta attempts veces; si resuelve devuelve; si falla siempre lanza último error
  
  let lastError: unknown;

  for (let index = 0; index < attempts; index++) {
    try{
      return await fn();
    }catch(error){
      lastError=error;
    }  
  }
  throw lastError;
}

export async function parallelSum(values: Array<Promise<number>>): Promise<number> {
  // Promise.all y suma; Error si alguno no es finito
  
  const response = await Promise.all(values);
  for(const n of response){
    if(!Number.isFinite(n)){
      throw new Error("Numero infinito");
    }
  }
  return response.reduce((acumulador, actual)  => acumulador+actual, 0);
}
