import { Component, Input, OnChanges } from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})
export class OrderFormComponent {
  @Input() product: any;
  orderForm: FormGroup;

  constructor(private fb: FormBuilder) {
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

  ngOnChanges() {
    if (this.product) {
      this.addProduct(this.product);
    }
  }

  addProduct(product: any) {
    const detalle = this.fb.group({
      producto: this.fb.group({ idProducto: [product.id] }),
      cantidad: [1],
      precioUnitario: [product.precio],
      subtotal: [product.precio]
    });
    this.detalles.push(detalle);
    this.updateTotal();
  }

  updateTotal() {
    const total = this.detalles.controls.reduce((sum, control) => sum + control.value.subtotal, 0);
    this.orderForm.patchValue({ total });
  }

  submitOrder() {
    console.log(this.orderForm.value);
  }
}
