import { Routes } from '@angular/router';
import { PaymentFormComponent } from './payment-form/payment-form.component';
import { PaymentsListComponent } from './payments-list/payments-list.component';


export const routes: Routes = [
  { path: '', component: PaymentsListComponent },
  { path: 'create', component: PaymentFormComponent },
  { path: 'edit/:id', component: PaymentFormComponent },
  { path: '**', redirectTo: '' }
];
