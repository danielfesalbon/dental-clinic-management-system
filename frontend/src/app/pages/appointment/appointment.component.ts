import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css'],
})
export class AppointmentComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  appointments: any[];
  total: any;
  row: any;
  page: any;
  options: any[];
  appointment: FormGroup;
  services: any[];

  timelist: any[];

  ngOnInit(): void {
    this.getservices();
    this.settimelist();
    this.appointment = this.fb.group({
      ptid: new FormControl(),
      ptfirstname: new FormControl('', Validators.required),
      ptlastname: new FormControl('', Validators.required),
      ptcontact: new FormControl('', Validators.required),
      scheddate: new FormControl(new Date(), Validators.required),
      schedtime: new FormControl('', Validators.required),
      service: new FormControl(''),
    });
    this.getappointments(10, 0);
  }

  getservices() {
    this.service.getallservicelist().subscribe(res => {
      this.services = res.services;
    }, err => {
      this.tokenService.checkSession(err);
    });
  }

  onSubmit(value: string) {
    let app = JSON.parse(JSON.stringify(value));
    app.schedtime = app.schedtime.value;
    app.service = app.service.name;
    this.confirmationService.confirm({
      message: 'Schedule new appointment',
      accept: () => {
        this.service.saveappointment(app).subscribe(res => {
          if (res.flag == 'success') {
            this.ngOnInit();
            this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
          }
        }, err => {
          this.tokenService.checkSession(err);
          this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.error.event });
        });
      },
    });
  }


  searchPatient(value: string) {
    this.appointment.reset();
    let p = JSON.parse(JSON.stringify(value));
    this.service.getpatient(p.ptid, 10, 0, 10, 0, 10, 0).subscribe(res => {
      let patient = res.patient;
      this.appointment.setValue({ ptid: patient.id, ptfirstname: patient.firstname, ptlastname: patient.lastname, ptcontact: patient.contact, scheddate: null, schedtime: null })
      this.messageService.add({ key: 'bc', severity: 'info', summary: 'Success', detail: 'Patient data fetched' });
    }, err => {
      this.tokenService.checkSession(err);
      if (err.status == 404) {
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: 'Not found' });
      }
    });
  }


  getappointments(row, page) {
    this.service.getappointments(row, page).subscribe(res => {
      this.appointments = res.appoinments;
      this.total = res.pagevalues.count;
      this.row = res.pagevalues.row;
      this.options = res.pagevalues.rowoptions;
      this.page = res.pagevalues.page;
    }, err => {
      this.tokenService.checkSession(err);
    });
  }

  viewappointmentdetails(id) {
    this.router.navigate(['back-office/main/appointment/' + id]);
  }


  settimelist() {
    this.timelist = [];
    for (let i = 7; i < 19; i++) {
      let meridiem = (i) < 12 ? 'AM' : 'PM';
      let n = (i) < 12 ? (i) : (i) - 12;
      let hrs = '' + n + '';
      if (hrs.length < 2) {
        hrs = '0' + hrs;
      }
      hrs = hrs + ':00 ' + meridiem;
      this.timelist.push({ value: hrs });
    }
  }
}
