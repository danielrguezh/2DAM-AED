// ejemplos/02-tipos-compuestos.ts
type UsuarioBasico = {
  id: number;
  nombre: string;
  email: string;
};

let u1: UsuarioBasico = {
  id: 1,
  nombre: "Ana",
  email: "ana@example.com",
};

console.log(`${u1.nombre}, ${u1.email}`);