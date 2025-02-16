import { Component, OnInit } from '@angular/core';
import { OrdenesService } from '../services/ordenes.service';

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

@Component({
  selector: 'app-ordenes',
  templateUrl: './ordenes.component.html',
  styleUrls: ['./ordenes.component.css']
})
export class OrdenesComponent implements OnInit {

  ordenes: Orden[] = [];

  constructor(private ordenesService: OrdenesService) {}

  ngOnInit(): void {
    this.obtenerOrdenes();
  }

  obtenerOrdenes(): void {
    this.ordenesService.getOrdenes()
      .subscribe(data => {
        this.ordenes = data;
      }, error => {
        console.error('Error al obtener órdenes', error);
      });
  }

}
