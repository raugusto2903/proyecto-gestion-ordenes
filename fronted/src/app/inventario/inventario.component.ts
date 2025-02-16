import { Component, OnInit, ChangeDetectorRef  } from '@angular/core';
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
  productosSinInventario: any[] = [];

  constructor(private fb: FormBuilder, private inventarioService: InventarioService,private productService: ProductService, private cdr: ChangeDetectorRef) {
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
    this.obtenerProductosSinInventario();
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
      const datosInventario = {
        producto: { idProducto: this.inventarioForm.value.productoId }, // ✅ Convertir a objeto
        cantidadDisponible: this.inventarioForm.value.cantidadDisponible,
        stockMinimo: this.inventarioForm.value.stockMinimo,
        stockMaximo: this.inventarioForm.value.stockMaximo
      };
  
      console.log("Datos enviados al backend:", datosInventario); // ✅ Verifica en consola
  
      this.inventarioService.addInventario(datosInventario).subscribe(
        response => {
          console.log("Inventario agregado:", response);
          this.obtenerInventarios();
          this.inventarioForm.reset();
        },
        error => { console.error('Error al agregar inventario', error); }
      );
    }
  }

  actualizarInventario(id: number): void {
    // Encuentra el inventario en la lista para obtener los datos actuales
    const inventario = this.inventarios.find(inv => inv.idInventario === id);
  
    if (!inventario) {
      console.error("Inventario no encontrado para el ID:", id);
      return;
    }
  
    const datosActualizados = {
      producto: { 
        idProducto: inventario.producto.idProducto,  // ✅ ID del producto
        nombre: inventario.producto.nombre,          // ✅ Nombre del producto
        descripcion: inventario.producto.descripcion,// ✅ Descripción
        precio: inventario.producto.precio,          // ✅ Precio
        categoria: inventario.producto.categoria,    // ✅ Categoría
        activo: inventario.producto.activo           // ✅ Estado
      },
      cantidadDisponible: inventario.cantidadDisponible,
      stockMinimo: inventario.stockMinimo,
      stockMaximo: inventario.stockMaximo
    };
  
    console.log("Datos enviados al backend para actualizar:", datosActualizados); // ✅ Verificar en consola
  
    this.inventarioService.updateInventario(id, datosActualizados).subscribe(
      response => {
        alert("Inventario actualizado:");
        console.log("Inventario actualizado:", response);
        this.obtenerInventarios(); // Recargar lista
      },
      error => { console.error('Error al actualizar inventario', error); }
    );
  }
  

  obtenerProductosSinInventario(): void {
    this.productService.getProductosSinInventario().subscribe(
      (data) => {
        console.log("Productos sin inventario:", data); // ✅ Verifica la estructura
        this.productosSinInventario = data;
        this.cdr.detectChanges();
      },
      (error) => { console.error('Error al obtener productos sin inventario', error); }
    );
  }

}
