import { Role } from "../models";

/**
 * E04 – Tuples y enum
 * @author danielrguezh
 * @version 1.0.0
 */

export type JwtParts = [header: string, payload: string, signature: string];

export function splitJwt(token: string): JwtParts {
  // "a.b.c" => [a,b,c] exactamente 3 partes, si no => Error
  const splitter: string[] = token.split(".");
  if (splitter.length !== 3){
    throw new Error("No coincide el numero de partes");
  }
  return [splitter[0], splitter[1], splitter[2]];
}

export function roleFromString(value: string): Role {
  // "ADMIN"|"USER" (case-insensitive) => Role; si no => Error
  if (Role.ADMIN.match(value.toUpperCase())){
    return Role.ADMIN;
  }
  if (Role.USER.match(value.toUpperCase())){
    return Role.USER;
  }

  throw new Error("El rol no existe");
}

export function formatUserTag(username: string, role: Role): string {
  // "juan", ADMIN => "juan#ADMIN" (username trim, no vacío)
  
  if(username.trim() == ""){
    throw new Error("nombre vacio");
  }
  return username.trim()+"#"+role.toString();
}
