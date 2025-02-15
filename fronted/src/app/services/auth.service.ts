import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../enviroments/enviroment';
import { lastValueFrom } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}`;
  constructor(private http: HttpClient) { }
  async login(username: string, password: string) {
    try {
      const response = await lastValueFrom(this.http.post<any>(`${this.apiUrl}/auth/login`, { username, password }));
      console.log('Respuesta del backend:', response);
      return response;
    } catch (error) {
      console.error('Error en la autenticación:', error);
      throw error;
    }
  }
  

  register(username: string, email: string, password: string): Observable<any> {
    const enabled = true;
    return this.http.post<any>(`${this.apiUrl}/api/usuarios`, { username, email, password, enabled });
  }

}
