import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-appointmentdetails',
  templateUrl: './appointmentdetails.component.html',
  styleUrls: ['./appointmentdetails.component.css']
})
export class AppointmentdetailsComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private route: ActivatedRoute
  ) { }


  appointment: FormGroup;
  app: any;
  users: any[];
  text1: any;
  services: any[];

  timelist: any[];

  ngOnInit(): void {
    this.settimelist();
    this.text1 = '';
    this.app = {};
    this.appointment = this.fb.group({
      scheddate: new FormControl(new Date(), Validators.required),
      schedtime: new FormControl(0, Validators.required),
      remarks: new FormControl(),
      doctor: new FormControl('', Validators.required),
      done: new FormControl(false, Validators.required),
      service: new FormControl(''),
    });
    //this.getusers();
    this.getservices();
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

  getservices() {
    this.service.getallservicelist().subscribe(res => {
      this.services = res.services;
      this.getusers();
    }, err => {
      this.tokenService.checkSession(err);
    });
  }


  getusers() {
    this.users = [];
    this.service.getusers().subscribe(res => {
      this.users = res;
      this.users.forEach(u => {
        let title = u.title != undefined && u.title != null ? u.title : '';
        let firstname = u.firstname != undefined && u.firstname != null ? u.firstname : '';
        let lastname = u.lastname != undefined && u.lastname != null ? u.lastname : '';
        u.name = title + ' ' + firstname + ' ' + lastname;
      });
      this.getappointmentdetails();
    }, err => {
      this.tokenService.checkSession(err);
    });
  }


  getappointmentdetails() {
    this.route.paramMap.subscribe(params => {
      this.service.getappointment(params.get('id')).subscribe(res => {
        this.app = res.appointment;
        //let timestamp = new Date(Date.parse('Thu, 01 Jan 1970 ' + this.app.schedtime));
        let timestamp = this.timelist.find(i => i.value == this.app.schedtime);
        let doc = this.users.find(i => i.username == this.app.doctor);
        let s = this.services.find(i => i.name == this.app.service);
        if (doc == undefined || doc == null) {
          doc = '';
        }
        if (s == undefined || s == null) {
          s = '';
        }
        this.appointment.setValue({ scheddate: new Date(this.app.scheddate), schedtime: timestamp, doctor: doc, remarks: this.app.remarks, done: this.app.done, service: s });
      }, err => {
        this.tokenService.checkSession(err);
      });

    });
  }


  onSubmit(value: string) {
    let a: any = JSON.parse(JSON.stringify(value));
    a.doctor = a.doctor.username;
    a.service = a.service.name;
    this.app.doctor = a.doctor;
    this.app.scheddate = a.scheddate;
    this.app.schedtime = a.schedtime.value;
    this.app.remarks = a.remarks;
    this.app.done = a.done;
    this.app.service = a.service;
    this.confirmationService.confirm({
      message: 'Update appointment details',
      accept: () => {
        this.service.updateappointment(this.app).subscribe(res => {
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


  deleteAppointment() {
    this.confirmationService.confirm({
      message: 'Make sure you inform the patient before proceeding.',
      accept: () => {
        this.service.deleteappointment(this.app.id).subscribe(res => {
          if (res.flag == 'success') {
            this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
            this.router.navigateByUrl("back-office/main/appointment");
          }
        }, err => {
          this.tokenService.checkSession(err);
          this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.error.event });
        });
      },
    });
  }

}
