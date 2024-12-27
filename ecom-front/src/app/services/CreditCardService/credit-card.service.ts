import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CreditCardService {
  private apiUrl = 'http://localhost:8083/api/v1/creditCard';

  constructor(private http: HttpClient) { }

  createCreditCard(creditCard: any): Observable<any> {
    return this.http.post(this.apiUrl, creditCard);
  }

  // Fetch the credit card details for a specific customer
  getCreditCardByCustomerId(customerId: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?customerId=${customerId}`);
  }
}