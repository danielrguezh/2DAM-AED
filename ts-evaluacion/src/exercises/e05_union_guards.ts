import { JwtPayload, Role } from "../models";

/**
 * E05 – Union + type guards (unknown) + JWT
 * @author danielrguezh
 * @version 1.0.0
 */

export function normalizeId(id: string | number): string {
  // number => String; string => trim; vacío => Error
  if(id ==null || id.toString().trim()==""){
    throw new Error("No hay id");
  }
  return id.toString().trim();
}

export function isJwtPayload(value: unknown): value is JwtPayload {
  // objeto no null con sub string no vacía, role USER/ADMIN, exp number finito >=0
  if (typeof value !== "object" || value === null) {
    return false;
  }

  const payload = value as JwtPayload;

  if (typeof payload.sub !== "string" || payload.sub.trim() === "") {
    return false;
  }

  if (payload.role !== 'USER' && payload.role !== 'ADMIN') {
    return false;
  }

  if (typeof payload.exp !== "number" || !Number.isFinite(payload.exp) || payload.exp < 0) {
    return false;
  }
  
  return true;
}

export function requireRole(payload: JwtPayload, role: Role): void {
  // lanza Error si payload.role != role
  if(payload.role != role){
    throw new Error("No coinciden los roles");
  }
  
}
