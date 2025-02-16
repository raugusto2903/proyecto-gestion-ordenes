import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { InventarioComponent } from './inventario/inventario.component';
import { OrdenesComponent } from './ordenes/ordenes.component';
import { AuthGuardService } from './services/auth.guard.service';
import { UsersComponent } from './users/users.component'


const routes: Routes = [
  { path: '', component: LoginComponent }, // Login por defecto
  { path: 'register', component: RegisterComponent }, // Página de registro
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuardService]  },
  { path: 'inventario', component: InventarioComponent },
  { path: 'ordenes', component: OrdenesComponent, canActivate: [AuthGuardService] },
  { path: 'usuarios', component: UsersComponent, canActivate: [AuthGuardService] },
  { path: '**', redirectTo: '' } // Redirige cualquier ruta desconocida al login
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
