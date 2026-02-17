import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomerDTO, CustomerResponse } from '../models/customer.model';

@Injectable({ providedIn: 'root' })
export class CustomerService {
    private readonly apiUrl = '/api/customers';

    constructor(private http: HttpClient) { }

    createCustomer(dto: CustomerDTO): Observable<CustomerResponse> {
        return this.http.post<CustomerResponse>(this.apiUrl, dto);
    }
}
