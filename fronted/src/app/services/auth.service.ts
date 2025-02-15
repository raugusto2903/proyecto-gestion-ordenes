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
  token: boolean = false;
  constructor(private http: HttpClient) { }
  async login(username: string, password: string) {
    try {
      const response = await lastValueFrom(this.http.post<any>(`${this.apiUrl}/auth/login`, { username, password }));
      console.log('Respuesta del backend:', response);
      this.token = true;
      this. isLoggedIn();
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

  isLoggedIn(): boolean {
    if(this.token)
    console.log("si lo llamo");
    else
    console.log("no lo llamo")
    return this.token; // Retorna true si hay un token
  }

  logout() {
    localStorage.removeItem('token');
  }

}
