import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { authInterceptorProviders } from './service/authinterceptor.service';

import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';
import { TooltipModule } from 'primeng/tooltip';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { PanelMenuModule } from 'primeng/panelmenu';
import { ToastModule } from 'primeng/toast';
import { BlockUIModule } from 'primeng/blockui';
import { FocusTrapModule } from 'primeng/focustrap';
import { AccordionModule } from 'primeng/accordion';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { ToolbarModule } from 'primeng/toolbar';
import { InplaceModule } from 'primeng/inplace';
import { PanelModule } from 'primeng/panel';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { CalendarModule } from 'primeng/calendar';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { CardModule } from 'primeng/card';
import { OrderListModule } from 'primeng/orderlist';
import { ListboxModule } from 'primeng/listbox';
import { FieldsetModule } from 'primeng/fieldset';
import { ChartModule } from 'primeng/chart';
import { CarouselModule } from 'primeng/carousel';
import { KeyFilterModule } from 'primeng/keyfilter';
import { DataViewModule } from 'primeng/dataview';
import { FileUploadModule, FileUpload } from 'primeng/fileupload';
import { InputSwitchModule } from 'primeng/inputswitch';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MenubarModule } from 'primeng/menubar';
import { TabViewModule } from 'primeng/tabview';
import { RippleModule } from 'primeng/ripple';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
import { MenuModule } from 'primeng/menu';
import { RatingModule } from 'primeng/rating';
import { DividerModule } from 'primeng/divider';
import { BadgeModule } from 'primeng/badge';
import { InputNumberModule } from 'primeng/inputnumber';
import { PaginatorModule } from 'primeng/paginator';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { EditorModule } from 'primeng/editor';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { ScrollTopModule } from 'primeng/scrolltop';
import { GMapModule } from 'primeng/gmap';




import { LoginComponent } from './pages/login/login.component';
import { MainComponent } from './pages/main/main.component';
import { UseraccountComponent } from './pages/useraccount/useraccount.component';
import { ConfigurationComponent } from './pages/configuration/configuration.component';
import { AudittrailComponent } from './pages/audittrail/audittrail.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { TxdetailsComponent } from './pages/txdetails/txdetails.component';
import { PatientComponent } from './pages/patient/patient.component';
import { RecordsComponent } from './pages/records/records.component';
import { AppointmentComponent } from './pages/appointment/appointment.component';
import { InvoiceComponent } from './pages/invoice/invoice.component';
import { PaymentComponent } from './pages/payment/payment.component';
import { AppointmentdetailsComponent } from './pages/appointmentdetails/appointmentdetails.component';
import { InventorydetailsComponent } from './pages/inventorydetails/inventorydetails.component';
import { InvoicedetailsComponent } from './pages/invoicedetails/invoicedetails.component';
import { FullCalendarModule } from 'primeng/fullcalendar';
import { SiteComponent } from './pages/site/site.component';
import { LandingpageComponent } from './pages/landingpage/landingpage.component';
import { ServicesComponent } from './pages/services/services.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    SettingsComponent,
    InventoryComponent,
    DashboardComponent,
    UseraccountComponent,
    ConfigurationComponent,
    AudittrailComponent,
    TxdetailsComponent,
    PatientComponent,
    RecordsComponent,
    AppointmentComponent,
    InvoiceComponent,
    PaymentComponent,
    AppointmentdetailsComponent,
    InventorydetailsComponent,
    InvoicedetailsComponent,
    SiteComponent,
    LandingpageComponent,
    ServicesComponent,
  ],
  imports: [
    RippleModule,
    FullCalendarModule,
    GMapModule,
    TabViewModule,
    KeyFilterModule,
    DataViewModule,
    InputNumberModule,
    MenuModule,
    ChartModule,
    FieldsetModule,
    ListboxModule,
    OrderListModule,
    CarouselModule,
    InputSwitchModule,
    EditorModule,
    CardModule,
    OverlayPanelModule,
    ReactiveFormsModule,
    CalendarModule,
    CheckboxModule,
    InputTextareaModule,
    DropdownModule,
    PaginatorModule,
    MessageModule,
    MessagesModule,
    PanelModule,
    InplaceModule,
    ToolbarModule,
    AvatarModule,
    AvatarGroupModule,
    RadioButtonModule,
    MenubarModule,
    ScrollPanelModule,
    AccordionModule,
    RatingModule,
    FocusTrapModule,
    DividerModule,
    BlockUIModule,
    ToastModule,
    FormsModule,
    ConfirmDialogModule,
    FileUploadModule,
    DialogModule,
    TooltipModule,
    PanelMenuModule,
    SidebarModule,
    BadgeModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    TableModule,
    HttpClientModule,
    BrowserAnimationsModule,
  ],
  providers: [
    ConfirmationService,
    MessageService,
    FileUpload,
    authInterceptorProviders,
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
