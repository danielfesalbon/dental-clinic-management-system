import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppointmentComponent } from './pages/appointment/appointment.component';
import { AppointmentdetailsComponent } from './pages/appointmentdetails/appointmentdetails.component';
import { AudittrailComponent } from './pages/audittrail/audittrail.component';
import { ConfigurationComponent } from './pages/configuration/configuration.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { InventorydetailsComponent } from './pages/inventorydetails/inventorydetails.component';
import { InvoiceComponent } from './pages/invoice/invoice.component';
import { InvoicedetailsComponent } from './pages/invoicedetails/invoicedetails.component';
import { LoginComponent } from './pages/login/login.component';
import { MainComponent } from './pages/main/main.component';
import { PatientComponent } from './pages/patient/patient.component';
import { PaymentComponent } from './pages/payment/payment.component';
import { RecordsComponent } from './pages/records/records.component';
import { UseraccountComponent } from './pages/useraccount/useraccount.component';
import { AuthguardService } from './service/authguard.service';

const routes: Routes = [
  { path: 'back-office/login', component: LoginComponent },
  { path: '', redirectTo: 'back-office/main', pathMatch: 'full' },
  {
    path: 'back-office/main',
    component: MainComponent,
    canActivate: [AuthguardService],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'patient', component: RecordsComponent },
      { path: 'patient/:id', component: PatientComponent },
      { path: 'users', component: UseraccountComponent },
      { path: 'inventory', component: InventoryComponent },
      { path: 'inventory/:id', component: InventorydetailsComponent },
      { path: 'appointment', component: AppointmentComponent },
      { path: 'appointment/:id', component: AppointmentdetailsComponent },
      { path: 'configuration', component: ConfigurationComponent },
      { path: 'invoice', component: InvoiceComponent },
      { path: 'invoice/:id', component: InvoicedetailsComponent },
      { path: 'system/activity', component: AudittrailComponent },
      { path: 'transaction', component: PaymentComponent }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
