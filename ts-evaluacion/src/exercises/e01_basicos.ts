/**
 * E01 – Tipos básicos: string/number/boolean/null/undefined
 * @author danielrguezh
 * @version 1.0.0
 */

export function normalizeBearer(authHeader: string): string {
  // trim + "Bearer <token>" (case-insensitive), colapsa espacios a 1, Error si inválido
  if (authHeader.trim() == ""){
    throw new Error();
  }
  
  const palabras: Array<String> = authHeader.trim().split(/\s+/);
  if (palabras[0].toLowerCase() !== "bearer" || palabras.length !== 2) {
    throw new Error();
  }

  palabras[0] = "Bearer";

  return palabras.join(" ");
}

export function clamp01(value: number): number {
  // Devuelve value limitado a [0,1]. Error si NaN o no finito.
  if(Number.isNaN(value) || !Number.isFinite(value) ){
    throw new Error();
  }
  if(value<0){
    value=0;
  }
  if(value>1){
    value=1;
  }
  return value;
}

export function safeBool(value: boolean | null | undefined): boolean {
  if(value == null || value == undefined){
    return false;
  }
  return value;
}
