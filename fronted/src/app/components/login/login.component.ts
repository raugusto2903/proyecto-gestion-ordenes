import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  async onSubmit() {
    try {
      const response = await this.authService.login(this.email, this.password);
      console.log('Autenticación exitosa:', response);
      
      if (response && response.token) {
        localStorage.setItem('token', response.token);
        this.router.navigate(['/dashboard']);
      } else {
        this.errorMessage = 'Respuesta inesperada del servidor';
      }
    } catch (error) {
      console.error('Error en la autenticación:', error);
      this.errorMessage = 'Credenciales incorrectas';
    }
  }
  
  
}
