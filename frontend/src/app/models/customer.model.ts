export interface CustomerDTO {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
}

export interface CustomerResponse {
  id: number;
  code: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  createdAt: string;
}
