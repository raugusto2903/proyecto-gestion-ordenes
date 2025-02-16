import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

interface ClienteFrecuente {
  usuario: {
    idUsuario: number;
    username: string;
    email: string;
    enabled: boolean;
    isAdmin: boolean;
    createdAt: string;
  };
  cantidadOrdenes: number;
}


@Injectable({
  providedIn: 'root',
})
export class UsersService {
  private apiUrl = 'http://localhost:8082/api/usuarios';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  getClientesFrecuentes(): Observable<any[]> {
    return this.http.get<ClienteFrecuente[]>(`${this.apiUrl}/frecuentes`).pipe(
      map((data) =>
        data.map((item) => ({
          ...item.usuario, // Extraemos los datos del usuario
          cantidadOrdenes: item.cantidadOrdenes, // Añadimos cantidad de órdenes
        }))
      )
    );
  }


}
