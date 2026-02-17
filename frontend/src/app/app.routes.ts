import { Routes } from '@angular/router';
import { CustomersComponent } from './components/customers/customers.component';
import { MenuComponent } from './components/menu/menu.component';
import { OrdersComponent } from './components/orders/orders.component';

export const routes: Routes = [
    { path: '', redirectTo: 'customers', pathMatch: 'full' },
    { path: 'customers', component: CustomersComponent },
    { path: 'menu', component: MenuComponent },
    { path: 'orders', component: OrdersComponent },
    { path: '**', redirectTo: 'customers' }
];
