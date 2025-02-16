import { Component, OnInit} from '@angular/core';
import { ProductService } from '../services/product.service';
import { AuthService } from '../services/auth.service';
import { OrderFormComponent } from '../order-form/order-form.component';
@Component({
  selector: 'app-dashboard',
  templateUrl:'./dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  criterioSeleccionado: string = '';
  nombre: string = '';
  precioMin: number | null = null;
  precioMax: number | null = null;
  categoria: string = '';
  products: any[] = []; // Lista de productos
  selectedProduct: any = null;
  userId: number = 0; // Variable para almacenar el userId
  showOrderForm: boolean = false;
 
  constructor(private productService: ProductService, private authService: AuthService) {
  }

  ngOnInit() {
    this.loadProducts();
    this.userId = this.authService.id;
  }

  

  loadProducts() {
    this.productService.getProducts().subscribe(
      data => {
        this.products = data;
        console.log('Productos cargados:', data);
      },
      error => {
        console.error('Error al obtener productos:', error);
      }
    );
  }

  buscar() {
    if (this.criterioSeleccionado === 'nombre' && this.nombre) {
      this.productService.getProducts().subscribe((data) => {
        this.products = data.filter(producto => 
          producto.nombre.toLowerCase().includes(this.nombre.toLowerCase())
        );
      });
    } else if (this.criterioSeleccionado === 'rango-precio' && this.precioMin !== null && this.precioMax !== null) {
      this.productService.getProducts().subscribe((data) => {
        this.products = data.filter(producto =>
          producto.precio >= this.precioMin! && producto.precio <= this.precioMax!
        );
      });
    } else if (this.criterioSeleccionado === 'categoria' && this.categoria) {
      this.productService.getProducts().subscribe((data) => {
        this.products = data.filter(producto => 
          producto.categoria.toLowerCase() === this.categoria.toLowerCase()
        );
      });
    } else if (this.criterioSeleccionado === 'mas-vendidos') {
      this.productService.obtenerProductosMasVendidos().subscribe((data) => {
        this.products = data;
      });
    } else {
      // Si no se selecciona ningún criterio, mostrar todos los productos por defecto
      this.productService.getProducts().subscribe((data) => {
        this.products = data;
      });
    }
  }

  

  selectProduct(product: any) {
    this.selectedProduct = { ...product, userId: this.userId };
    if(this.selectedProduct.userId > 0){
      this.showOrderForm = true;
    }
   // 🔥 Asegura que esta línea se ejecuta
    console.log('Producto seleccionado con userId:', this.selectedProduct, 'showOrderForm:', this.showOrderForm);
  }
}
