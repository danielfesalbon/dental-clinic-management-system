import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,) { }

  ngOnInit(): void {
    this.servicefrm = this.fb.group({
      name: new FormControl('', Validators.required),
    });
    this.getservices(10, 0);
  }

  services: any[];
  total: any;
  row: any;
  page: any;
  options: any[];
  servicefrm: FormGroup;


  getservices(row, page) {
    this.service.getservicelist(row, page).subscribe(res => {
      this.services = res.services;
      this.total = res.pagevalues.count;
      this.row = res.pagevalues.row;
      this.options = res.pagevalues.rowoptions;
      this.page = res.pagevalues.page;
    }, err => {
      this.tokenService.checkSession(err);
    });
  }

  onSubmit(value: string) {
    let s = JSON.parse(JSON.stringify(value));
    s.dateadded = new Date();
    s.addedby = this.tokenService.getUser();
    this.confirmationService.confirm({
      message: 'Add new service',
      accept: () => {
        this.service.saveservice(s).subscribe(res => {
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

  deleteservice(id) {
    this.confirmationService.confirm({
      message: 'Delete service',
      accept: () => {
        this.service.deleteservice(id).subscribe(
          res => {
            if (res.flag == 'success') {
              this.ngOnInit();
              this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event });
            }
          },
          (err) => {
            this.tokenService.checkSession(err);
            this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });
          }
        );
      },
    });
  }

}
