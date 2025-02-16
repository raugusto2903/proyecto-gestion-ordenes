import { Component, Input, OnChanges, SimpleChanges, EventEmitter, Output} from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { OrdenesService } from '../services/ordenes.service';


@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})
export class OrderFormComponent implements OnChanges {
  @Input() product!: any;
  @Output() closeForm = new EventEmitter<void>();
  orderForm: FormGroup;
  userName: string = '';

  constructor(private fb: FormBuilder, private ordenesService: OrdenesService) {
    this.orderForm = this.fb.group({
      usuario: this.fb.group({ idUsuario: [1] }),
      detalles: this.fb.array([]),
      total: [0],
      estado: ['Pendiente']
    });
  }

  get detalles() {
    return this.orderForm.get('detalles') as FormArray;
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['product'] && changes['product'].currentValue) {
      this.addProduct(this.product);
    }
  }

  addProduct(product: any) {
    if (!product || !product.idProducto || !product.precio) {
      console.error("Error: El producto no tiene los datos necesarios", product);
      return;
    }
  
    console.log("Producto agregado:", JSON.stringify(product, null, 2));
  
    const detalle = this.fb.group({
      producto: this.fb.group({ idProducto: [product.idProducto],nombre:[product.nombre] }),
      cantidad: [1],  // 🔥 Asegúrate de incluir este campo
      precioUnitario: [product.precio],
      subtotal: [product.precio]
    });
    console.log("Detalle creado:", detalle.value);

    this.detalles.push(detalle);
    this.updateTotal();
  }

  ngOnInit() {
    this.loadUserData();
  }

  loadUserData() {
    const user = document.getElementById('user') as HTMLInputElement;
    if (user) {
      const userId = user.getAttribute("value");
      this.orderForm.patchValue({ usuario: { idUsuario: userId } });
    }
  }

  updateSubtotal(index: number) {
    const detalle = this.detalles.at(index) as FormGroup;
  
    // Tomar valores actualizados directamente del formulario
    const cantidad = detalle.get('cantidad')?.value || 1;
    const precioUnitario = detalle.get('precioUnitario')?.value || 0;
    const subtotal = cantidad * precioUnitario;
  
    console.log(`Actualizando subtotal en el índice ${index}: cantidad=${cantidad}, precioUnitario=${precioUnitario}, subtotal=${subtotal}`);
  
    // Asigna el nuevo subtotal en el FormControl
    detalle.patchValue({ subtotal });
  
    // Recalcular el total de la orden
    this.updateTotal();
  }
  


  updateTotal() {
    const total = this.detalles.controls.reduce((sum, control) => sum + control.value.subtotal, 0);
    this.orderForm.patchValue({ total });
  }

  submitOrder() {
    if (this.orderForm.valid) {
      const orderData = this.orderForm.getRawValue(); // Obtener los datos incluyendo los campos deshabilitados
      console.log('Enviando orden al servicio:', orderData);

      this.ordenesService.createOrder(orderData).subscribe(
        response => {
          console.log('Orden creada con éxito:', response);
          alert('Orden creada exitosamente!');
          this.orderForm.reset(); // Reiniciar el formulario tras éxito
          this.closeForm.emit(); // 🔥 Emitir evento para cerrar el formulario
        },
        error => {
          console.error('Error al crear la orden:', error);
          alert('Error al crear la orden');
        }
      );
    } else {
      console.error('El formulario no es válido.');
    }
  }
}
