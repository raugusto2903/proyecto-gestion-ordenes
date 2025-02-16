import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-crearproducto',
  templateUrl: './crearproducto.component.html',
  styleUrls: ['./crearproducto.component.css']
})
export class CrearproductoComponent {
  productoForm: FormGroup;
  
  constructor(private fb: FormBuilder, private productService: ProductService, private router: Router) {
    this.productoForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      precio: ['', [Validators.required, Validators.min(0)]],
      categoria: ['', Validators.required],
      activo: [true, Validators.required]
    });
  }

  crearProducto(): void {
    if (this.productoForm.valid) {
      this.productService.addProducto(this.productoForm.value).subscribe(
        response => {
          alert('Producto creado');
          this.productoForm.reset();
          this.router.navigate(['/inventario']);
        },
        error => { console.error('Error al crear producto', error); }
      );
    }
  }
}
