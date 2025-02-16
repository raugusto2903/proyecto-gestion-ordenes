import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Orden {
  id: number;
  usuario: {
    username: string;
    email: string;
  };
  detalles: {
    producto: {
      nombre: string;
    };
    cantidad: number;
    subtotal: number;
  }[];
  total: number;
  estado: string;
  fechaCreacion: string;
}

@Injectable({
  providedIn: 'root'
})
export class OrdenesService {

  private apiUrl = 'http://localhost:8082/api/ordenes'; // Reemplazar con el endpoint real

  constructor(private http: HttpClient) {}

  getOrdenes(): Observable<Orden[]> {
    return this.http.get<Orden[]>(this.apiUrl);
  }
  createOrder(orderData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, orderData);
  }
}
