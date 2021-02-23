import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css'],
})
export class RecordsComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  records: any[];
  total: any;
  row: any;
  page: any;
  options: any[];
  patient: FormGroup;
  ptbday: Date;
  genderoptions: any[];
  patientupload: any[];


  ngOnInit(): void {
    this.patientupload = [];
    this.genderoptions = [{ value: 'M' }, { value: 'F' }];
    this.patient = this.fb.group({
      firstname: new FormControl('', Validators.required),
      lastname: new FormControl('', Validators.required),
      middlename: new FormControl(''),
      address: new FormControl('', Validators.required),
      contact: new FormControl('', Validators.required),
      birthdate: new FormControl(new Date(), Validators.required),
      email: new FormControl('', Validators.email),
      gender: new FormControl(''),
    });

    this.getpatients(10, 0);
  }

  onSubmit(value: string) {
    let patient: any = JSON.parse(JSON.stringify(value));
    patient.gender = patient.gender.value;
    patient.createdby = this.tokenService.getUser();
    this.confirmationService.confirm({
      message: 'Save new patient',
      accept: () => {
        this.service.savepatient(patient).subscribe(
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


  getpatients(row, page) {
    this.service.getpatients(row, page).subscribe(res => {
      this.records = res.patient;
      this.total = res.pagevalues.count;
      this.row = res.pagevalues.row;
      this.options = res.pagevalues.rowoptions;
      this.page = res.pagevalues.page;
    }, err => {
      this.tokenService.checkSession(err);
    });
  }

  paginate(event) {
    this.page = event.page;
    this.row = event.rows;
    this.getpatients(this.row, this.page);
  }


  viewpatientdetails(id) {
    this.router.navigate(['back-office/main/patient/' + id]);
  }

  handleFileInput(ev) {
    if (ev.target.files.length > 0) {
      this.patientupload = [];
      let workBook = null;
      let jsonData = null;
      const reader = new FileReader();
      const file = ev.target.files[0];
      reader.onload = (event) => {
        const data = reader.result;
        workBook = XLSX.read(data, { type: 'binary' });
        jsonData = workBook.SheetNames.reduce((initial, name) => {
          const sheet = workBook.Sheets[name];
          initial[name] = XLSX.utils.sheet_to_json(sheet);
          return initial;
        }, {});
        for (var key in jsonData) {
          if (jsonData.hasOwnProperty(key)) {
            //this.patientupload.push(jsonData[key]);
            this.patientupload = jsonData[key];
          }
        }
      }
      reader.readAsBinaryString(file);
    }
  }

  onBrowse() {
    let label = document.getElementById('upload-label');
    label.click();
  }

  uploadallpatient() {
    this.confirmationService.confirm({
      message: 'Save new patient',
      accept: () => {
        this.service.saveallpatient(this.patientupload).subscribe(res => {
          if (res.flag == 'success') {
            this.ngOnInit();
            this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
          }
        },
          err => {
            this.tokenService.checkSession(err);
            this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });
          });
      },
    });
  }



  refresh() {
    location.reload();
  }

}
