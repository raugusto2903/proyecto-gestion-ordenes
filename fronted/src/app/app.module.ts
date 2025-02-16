import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { InventarioComponent } from './inventario/inventario.component';
import { ReactiveFormsModule } from '@angular/forms';
import { OrdenesComponent } from './ordenes/ordenes.component';
import { UsersComponent } from './users/users.component';
import { OrderFormComponent } from './order-form/order-form.component';  // ✅ Importa ReactiveFormsModule
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    InventarioComponent,
    OrdenesComponent,
    UsersComponent,
    OrderFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule, // ✅ Agrégalo aquí
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
