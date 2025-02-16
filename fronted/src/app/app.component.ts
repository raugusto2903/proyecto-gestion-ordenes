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
  userName: string | null = null;
  userId: number = 0;
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
    if (this.isLoggedIn) {
      this.userName = this.authService.username;
      this.userId = this.authService.id;
    }
  }

  logout() {
    this.authService.logout();
    this.isLoggedIn = false;
    window.location.href = '/login';
  }

}
