import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';

export interface Payment {
  id?: number;
  reference?: string;
  amount: number;
  currency: string;
  createdAt?: string;
  clientRequestId: string;
}

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private api = '/api/payments';

  constructor(private http: HttpClient) {}

  list(): Observable<Payment[]> {
    return this.http.get<Payment[]>(this.api);
  }

  get(id: number): Observable<Payment> {
    return this.http.get<Payment>(`${this.api}/${id}`);
  }

  create(amount: number, currency: string): Observable<Payment> {
    const body = {
      amount,
      currency,
      clientRequestId: uuidv4()
    };
    return this.http.post<Payment>(this.api, body);
  }

  update(id: number, amount: number, currency: string, clientRequestId: string): Observable<Payment> {
    return this.http.put<Payment>(`${this.api}/${id}`, {
      amount,
      currency,
      clientRequestId
    });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}
