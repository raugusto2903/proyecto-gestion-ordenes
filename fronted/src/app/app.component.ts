import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Router, NavigationEnd } from '@angular/router'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  isLoggedIn: boolean = false;
 
  constructor(private authService: AuthService, private router: Router) {
    console.log('Estado de isLoggedIn:', this.isLoggedIn);
  }
  
  
  
  ngOnInit() {
    this.checkLoginStatus(); // ✅ Verifica el estado al iniciar

    // ✅ Detecta cambios de ruta y vuelve a ejecutar `checkLoginStatus`
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.checkLoginStatus();
      }
    });
  }

  checkLoginStatus() {
    this.isLoggedIn = this.authService.isLoggedIn();
    console.log('Estado de isLoggedIn después del cambio de ruta:', this.isLoggedIn);
  }

  logout() {
    this.authService.logout();
    this.isLoggedIn = false;
    window.location.href = '/login';
  }

}
