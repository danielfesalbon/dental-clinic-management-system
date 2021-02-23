import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';


@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private route: ActivatedRoute
  ) { }

  patient: any;
  appointment: FormGroup;
  details: FormGroup;
  prescform: FormGroup;
  patientform: FormGroup;

  apptotal: any;
  approw: any;
  apppage: any;
  appoptions: any[];
  applist: any[];

  txtotal: any;
  txrow: any;
  txpage: any;
  txoptions: any[];
  txlist: any[];

  presctotal: any;
  prescrow: any;
  prescpage: any;
  prescoptions: any[];
  presclist: any[];
  prmodal: boolean;
  presc: any;

  genderoptions: any[];

  ngOnInit(): void {
    this.presc = {};
    this.prmodal = false;
    this.patient = {};
    this.genderoptions = [{ value: 'M' }, { value: 'F' }];
    this.getpatient(10, 0, 10, 0, 10, 0);
    this.appointment = this.fb.group({
      scheddate: new FormControl(new Date(), Validators.required),
      schedtime: new FormControl(0, Validators.required),
    });
    this.details = this.fb.group({
      email: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      contact: new FormControl('', Validators.required),
    });
    this.prescform = this.fb.group({
      presremarks: new FormControl('', Validators.required),
      active: new FormControl(true, Validators.required),
    });
    this.patientform = this.fb.group({
      id: new FormControl(),
      firstname: new FormControl({ value: '', disabled: true }),
      lastname: new FormControl({ value: '', disabled: true }),
      middlename: new FormControl(''),
      address: new FormControl(''),
      contact: new FormControl('', Validators.required),
      birthdate: new FormControl({ value: new Date(), disabled: true }, Validators.required),
      email: new FormControl(''),
      gender: new FormControl(''),
      datecreated: new FormControl(),
      createdby: new FormControl(),
    });
  }

  getpatient(approw, appage, txrow, txpage, prescrow, prescpage) {
    this.route.paramMap.subscribe(params => {
      this.service.getpatient(params.get('id'), approw, appage, txrow, txpage, prescrow, prescpage).subscribe(res => {
        this.patient = res.patient;
        this.patient.birthdate = new Date(this.patient.birthdate);
        let g = this.genderoptions.find(i => i.value == this.patient.gender);
        this.patient.gender = g != undefined ? g : '';
        this.patientform.setValue(this.patient);

        this.applist = res.appointments;
        this.apptotal = res.apppage.count;
        this.approw = res.apppage.row;
        this.apppage = res.apppage.pages;
        this.appoptions = res.apppage.rowoptions;

        this.txlist = res.invoices
        this.txtotal = res.invpage.count;
        this.txrow = res.invpage.row;
        this.txpage = res.invpage.pages;
        this.txoptions = res.invpage.rowoptions;

        this.presclist = res.prescriptions;
        this.presctotal = res.prpage.count;
        this.prescrow = res.prpage.row;
        this.prescpage = res.prpage.pages;
        this.prescoptions = res.prpage.rowoptions;

      }, err => {
        this.tokenService.checkSession(err);
      });

    });
  }

  newprescription() {
    this.presc = {};
    this.prmodal = true;
    this.prescform.reset();
  }

  viewprescription(data) {
    this.presc = data;
    console.log(this.presc);
    this.prmodal = true;
    this.prescform.setValue({ presremarks: data.presremarks, active: data.active });
  }

  prescriptionStatus(data) {
    if (data) {
      return 'Prescribed';
    } else {
      return 'Unprescribed';
    }
  }

  submitPrescription(value: string) {
    let pr: any = JSON.parse(JSON.stringify(value));
    pr.ptid = this.patient.id;
    pr.prescribedby = this.tokenService.getUser();
    pr.dateprescribed = new Date();
    if (this.presc != undefined && this.presc != null && this.presc.id != undefined && this.presc.id != null) {
      pr.id = this.presc.id;
    }
    this.confirmationService.confirm({
      message: 'Create new invoice',
      accept: () => {
        this.service.saveprescription(pr).subscribe(res => {
          if (res.flag == 'success') {
            this.ngOnInit();
            this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
          }
        }, err => {
          this.tokenService.checkSession(err);
          this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });

        });
      },
    });
  }

  onSubmit(value: string) {
    let patient: any = JSON.parse(JSON.stringify(value));
    patient.gender = patient.gender.value;
    this.confirmationService.confirm({
      message: 'Save new patient',
      accept: () => {
        this.service.updatepatient(patient).subscribe(
          res => {
            if (res.flag == 'success') {
              this.ngOnInit();
              this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
            }
          },
          err => {
            this.tokenService.checkSession(err);
            this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });
          }
        );
      },
    });
  }


  paginateApp(event) {
    this.apppage = event.page;
    this.approw = event.rows;
    this.getpatient(this.approw, this.apppage, this.txrow, this.txpage, this.prescrow, this.prescpage);
  }

  paginateTx(event) {
    this.txpage = event.page;
    this.txrow = event.rows;
    this.getpatient(this.approw, this.apppage, this.txrow, this.txpage, this.prescrow, this.prescpage);
  }

  paginatePresc(event) {
    this.prescpage = event.page;
    this.prescrow = event.rows;
    this.getpatient(this.approw, this.apppage, this.txrow, this.txpage, this.prescrow, this.prescpage);
  }

  viewappointmentdetails(id) {
    this.router.navigate(['back-office/main/appointment/' + id]);
  }


  viewinvoice(id) {
    this.router.navigate(['back-office/main/invoice/' + id]);
  }

}
