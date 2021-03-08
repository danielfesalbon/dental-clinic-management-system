import { Component, HostListener, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrls: ['./landingpage.component.css']
})
export class LandingpageComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  appointment: FormGroup;
  timelist: any[];
  appmodal: boolean;
  details: any;
  services: any[];


  ngOnInit(): void {
    this.getservices();
    this.getdetails();
    this.appmodal = false;
    this.settimelist();
    this.appointment = this.fb.group({
      ptid: new FormControl(),
      ptfirstname: new FormControl('', Validators.required),
      ptlastname: new FormControl('', Validators.required),
      ptcontact: new FormControl('', Validators.required),
      scheddate: new FormControl(new Date(), Validators.required),
      schedtime: new FormControl('', Validators.required),
      service: new FormControl('')
    });
  }

  newappointment() {
    this.appointment.reset();
    this.appmodal = true;
  }

  searchPatient(value: string) {
    this.appointment.reset();
    let p = JSON.parse(JSON.stringify(value));
    this.service.getpatientbyname(p.ptid, p.ptfirstname, p.ptlastname).subscribe(res => {
      let patient = res.patient;
      this.appointment.setValue({ ptid: patient.id, ptfirstname: patient.firstname, ptlastname: patient.lastname, ptcontact: patient.contact, scheddate: null, schedtime: null, service: null })
      this.messageService.add({ key: 'bc', severity: 'info', summary: 'Success', detail: 'Patient data fetched' });
    }, err => {
      console.log(err);
      this.tokenService.checkSession(err);
      if (err.status == 404) {
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: 'Not found' });
      } else {
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.error.message });
      }
    });
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


  scroll(id) {
    let el = document.getElementById(id);
    el.scrollIntoView(true);
  }

  @HostListener('window:scroll', ['$event']) // for window scroll events
  onScroll(event) {
    let el = document.getElementById('top-button');
    let nav = document.getElementById('stloginnav');
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
      el.style.display = "block";
      nav.style.backgroundColor = '#99dfdc';
      nav.classList.add('nav-shadow');
    } else {
      el.style.display = "none";
      nav.style.backgroundColor = '';
      nav.classList.remove('nav-shadow');
    }
  }

  toTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }

  getdetails() {
    this.details = {};
    this.details.contact1 = 'Not available';
    this.details.contact2 = 'Not available';
    this.details.address = 'Not available';
    this.details.email = 'Not available';
    this.service.getdentaldetails().subscribe(res => {
      this.details = res.details;
      this.details.contact1 = res.details.contact1 != undefined && res.details.contact1 != null ? res.details.contact1 : 'Not available';
      this.details.contact2 = res.details.contact2 != undefined && res.details.contact2 != null ? res.details.contact2 : 'Not available';
      this.details.address = res.details.address != undefined && res.details.address != null ? res.details.address : 'Not available';
      this.details.email = res.details.email != undefined && res.details.email != null ? res.details.email : 'Not available';
    }, err => { });
  }

  onSubmit(value: string) {
    let app = JSON.parse(JSON.stringify(value));
    app.schedtime = app.schedtime.value;
    app.service = app.service.name;
    this.confirmationService.confirm({
      key: 'landing-conf',
      message: 'Schedule new appointment',
      accept: () => {
        this.service.saveappointment(app).subscribe(res => {
          if (res.flag == 'success') {
            this.appmodal = false;
            this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
          }
        }, err => {
          this.tokenService.checkSession(err);
          this.appmodal = false;
          this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.error.event });
        });
      },
    });
  }

  getservices() {
    this.services = [];
    this.service.getallservicelist().subscribe(res => {
      this.services = res.services;
    }, err => { });
  }



}
