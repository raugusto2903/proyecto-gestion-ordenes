import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InventarioService } from '../services/inventario.service';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-inventario',
  templateUrl: './inventario.component.html',
  styleUrls: ['./inventario.component.css']
})
export class InventarioComponent implements OnInit{

  inventarioForm: FormGroup;
  inventarios: any[] = [];
  productos: any[] = [];

  constructor(private fb: FormBuilder, private inventarioService: InventarioService,private productService: ProductService) {
    this.inventarioForm = this.fb.group({
      productoId: ['', Validators.required],
      cantidadDisponible: ['', [Validators.required, Validators.min(0)]],
      stockMinimo: ['', [Validators.required, Validators.min(0)]],
      stockMaximo: ['', [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit(): void {
    this.obtenerInventarios();
    this.obtenerProductos();
  }

  obtenerInventarios(): void {
    this.inventarioService.getInventarios().subscribe(
      (data) => { this.inventarios = data; },
      (error) => { console.error('Error al obtener inventarios', error); }
    );
  }

  obtenerProductos(): void {
    this.productService.getProducts().subscribe(
      (data) => { this.productos = data; },
      (error) => { console.error('Error al obtener productos', error); }
    );
  }

  agregarInventario(): void {
    if (this.inventarioForm.valid) {
      this.inventarioService.addInventario(this.inventarioForm.value).subscribe(
        (response) => {
          this.obtenerInventarios();
          this.inventarioForm.reset();
        },
        (error) => { console.error('Error al agregar inventario', error); }
      );
    }
  }

  actualizarInventario(id: number, cantidad: number): void {
    this.inventarioService.updateInventario(id, { cantidadDisponible: cantidad }).subscribe(
      () => this.obtenerInventarios(),
      (error) => console.error('Error al actualizar inventario', error)
    );
  }

}
