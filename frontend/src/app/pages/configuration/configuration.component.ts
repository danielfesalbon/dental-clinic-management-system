import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css'],
})
export class ConfigurationComponent implements OnInit {
  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  prop: any;
  details: any;

  ngOnInit(): void {
    this.details = {};
    this.prop = {};
    this.getsettings();
  }

  getsettings() {
    this.details = {};
    this.prop = {};
    this.service.getsettings().subscribe(
      (res) => {
        this.prop = res;
        this.getdetails();
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  getdetails() {
    this.details = {};
    this.service.getdentaldetails().subscribe(res => {
      this.details = res.details;
    }, err => {
      this.tokenService.checkSession(err);
    })
  }

  savedetails() {
    this.confirmationService.confirm({
      message: 'Update clinic details',
      accept: () => {
        this.service.updatedentaldetails(this.details).subscribe(res => {
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
}
