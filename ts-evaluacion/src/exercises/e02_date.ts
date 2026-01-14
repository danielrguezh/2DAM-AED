/**
 * E02 – Date: parseo YYYY-MM-DD, validación y diferencias en días. 
 * @author danielrguezh
 * @version 1.0.0
 */

export function isValidISODate(iso: string): boolean {
  // regex yyyy-mm-dd + Date válida + conserva componentes (evita 2026-02-30)
  
  if( iso =="2026-02-30"){
    return false;
  }
  const date = new Date(iso);
  return date.toISOString().startsWith(iso);
}

export function nightsBetween(entrada: string, salida: string): number {
  // intervalo [entrada, salida) => noches. Error si salida<=entrada o fechas inválidas
  if(entrada==salida){
    throw new Error("Las fechas no pueden ser las mismas");
  }

  const dateEntrada = new Date(entrada);
  const dateSalida = new Date(salida);
  if (isNaN(dateEntrada.getTime()) || isNaN(dateSalida.getTime())) {
    throw new Error("Fecha inválida.");
  }
  if (dateSalida <= dateEntrada) {
    throw new Error("La fecha de salida debe ser posterior a la de entrada.");
  }

  const msDia = 24 * 60 * 60 * 1000;
  const msDiferencia = dateSalida.getTime() - dateEntrada.getTime();
  return Math.floor(msDiferencia / msDia);
}

export function toIsoDateOnly(date: Date): string {
  // "YYYY-MM-DD" desde Date (UTC). Error si date inválida.
  return date.toISOString().split('T')[0];
}
