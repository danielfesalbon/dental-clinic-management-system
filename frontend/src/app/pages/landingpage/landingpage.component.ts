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


  ngOnInit(): void {
    this.appmodal = false;
    this.settimelist();
    this.appointment = this.fb.group({
      ptid: new FormControl(),
      ptfirstname: new FormControl('', Validators.required),
      ptlastname: new FormControl('', Validators.required),
      ptcontact: new FormControl('', Validators.required),
      scheddate: new FormControl(new Date(), Validators.required),
      schedtime: new FormControl('', Validators.required),
    });
  }

  newappointment() {
    this.appointment.reset();
    this.appmodal = true;
  }

  searchPatient(value: string) {
    this.appointment.reset();
    let p = JSON.parse(JSON.stringify(value));
    this.service.getpatient(p.ptid).subscribe(res => {
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

  onSubmit(value: string) {
    let app = JSON.parse(JSON.stringify(value));
    app.schedtime = app.schedtime.value;
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

}
