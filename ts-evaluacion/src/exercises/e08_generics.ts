/**
 * E08 – Genéricos
 * @author danielrguezh
 * @version 1.0.0
 */

export function first<T>(items: T[]): T {
  // devuelve primer elemento, Error si vacío

  if (items == null || items.length<1){
    throw new Error("No se encuentrean en elementos");
  }
  return items[0];
}

export function unique<T>(items: T[]): T[] {
  // devuelve array sin duplicados preservando orden (usa Set internamente)
  const setDeItems = new Set<T>(items);
  return [...setDeItems];
}

export function groupBy<T, K extends string | number>(items: T[], keyFn: (item: T) => K): Record<K, T[]> {
  // agrupa por clave y devuelve Record (arrays nuevos)
  
  const map = new Map<K,T[]>();
  for(const item of items){
    const key = keyFn(item);
    const group = map.get(key);
    if (group){
      group.push(item);
    }else{
      map.set(key, [item]);
    }
  }
  return Object.fromEntries(map) as Record<K,T[]>;
}
