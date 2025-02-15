import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private apiUrl = 'http://localhost:8082/api/inventario';
  constructor(private http: HttpClient) { }
  getInventarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  addInventario(inventario: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, inventario);
  }

  updateInventario(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/actualizar/${id}`, data);
  }
}
