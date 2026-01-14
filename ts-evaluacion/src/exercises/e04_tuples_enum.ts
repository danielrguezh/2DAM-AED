import { Role } from "../models";

/**
 * E04 – Tuples y enum
 * @author danielrguezh
 * @version 1.0.0
 */

export type JwtParts = [header: string, payload: string, signature: string];

export function splitJwt(token: string): JwtParts {
  // "a.b.c" => [a,b,c] exactamente 3 partes, si no => Error
  throw new Error("TODO");
}

export function roleFromString(value: string): Role {
  // "ADMIN"|"USER" (case-insensitive) => Role; si no => Error
  throw new Error("TODO");
}

export function formatUserTag(username: string, role: Role): string {
  // "juan", ADMIN => "juan#ADMIN" (username trim, no vacío)
  throw new Error("TODO");
}
