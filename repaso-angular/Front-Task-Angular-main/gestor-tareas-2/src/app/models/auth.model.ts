export interface Auth {
  username:string,
  password:string
}
export type NewAuth = Omit<Auth, 'id'>;