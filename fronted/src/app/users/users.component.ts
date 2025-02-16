import { Component, OnInit } from '@angular/core';
import { UsersService } from '../services/users.service';

interface User {
  idUsuario: number;
  username: string;
  email: string;
  enabled: boolean;
  isAdmin: boolean;
  createdAt: string;
  cantidadOrdenes?: number; // Nueva propiedad para las órdenes
}

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  mostrandoFrecuentes: boolean = false; // Para alternar entre usuarios normales y clientes frecuentes

  constructor(private usersService: UsersService) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void {
    this.usersService.getUsers().subscribe(
      (data: User[]) => {
        this.users = data.map(user => ({ ...user, cantidadOrdenes: 0 })); // Inicializamos en 0 para usuarios normales
        this.mostrandoFrecuentes = false;
      },
      error => {
        console.error('Error al obtener usuarios', error);
      }
    );
  }

  fetchClientesFrecuentes(): void {
    this.usersService.getClientesFrecuentes().subscribe(
      (data: User[]) => {
        this.users = data; // Ya incluye `cantidadOrdenes`
        this.mostrandoFrecuentes = true;
      },
      error => {
        console.error('Error al obtener clientes frecuentes', error);
      }
    );
  }
}
