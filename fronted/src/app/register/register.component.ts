import { Component, Injectable } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { environment } from '../../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  name: string = '';
  email: string = '';
  password: string = '';
  errorMessage: string = '';
  successMessage: string='';

  constructor(private authService: AuthService, private router: Router) {}

  onRegister() {
    this.authService.register(this.name, this.email, this.password).subscribe(
      response => {
          // ✅ Mostramos mensaje de éxito
    this.successMessage = "Registro exitoso. Redirigiendo al login...";

    // ⏳ Esperamos 2 segundos antes de redirigir
    setTimeout(() => {
      this.router.navigate(['/']);
    }, 5000);
      },
      error => {
        this.errorMessage = 'Error al registrarse, intenta nuevamente';
      }
    );
  }
}
