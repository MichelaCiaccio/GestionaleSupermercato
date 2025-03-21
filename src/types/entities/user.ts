export type UserRole = 'admin' | 'manager' | 'cashier';

export interface User {
  operatorCode: string;
  name: string;
  surname: string;
  email: string;
  password: string;
  role: UserRole;
}
