import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin  } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
interface Producto {
  idProducto: number;
  nombre: string;
  precio: number;
  categoria: string;
  ventas: number;
}
interface ProductoMasVendidoDTO {
  producto: Producto;
  ventas: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8082/api/productos';
  constructor(private http: HttpClient) { 
  }
  getProducts(): Observable<any[]>  {
    return this.http.get<any[]>(this.apiUrl);
  }

  obtenerPorNombre(nombre: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/nombre?nombre=${nombre}`);
  }

  obtenerPorRangoDePrecios(precioMin: number, precioMax: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/rango-precio?precioMin=${precioMin}&precioMax=${precioMax}`);
  }

  obtenerPorCategoria(categoria: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/categoria?categoria=${categoria}`);
  }

  obtenerProductosMasVendidos(): Observable<Producto[]> {
    return this.http.get<ProductoMasVendidoDTO[]>(`${this.apiUrl}/mas-vendidos`).pipe(
      switchMap((masVendidos) => {
        const peticiones = masVendidos.slice(0, 5).map((producto) =>
          this.http.get<Producto>(`${this.apiUrl}/${producto.producto.idProducto}`) // Buscar cada producto por ID
        );
        return forkJoin(peticiones);
      })
    );
  }

  getProductosSinInventario(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/sin-inventario`);
  }
  addProducto(producto: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, producto);
  }
}
