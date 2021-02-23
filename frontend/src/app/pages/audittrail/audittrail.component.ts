import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-audittrail',
  templateUrl: './audittrail.component.html',
  styleUrls: ['./audittrail.component.css'],
})
export class AudittrailComponent implements OnInit {
  constructor(
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  list: any[];
  total: any;
  row: any;
  options: any[];

  ngOnInit(): void {
    this.getpages(10);
  }

  getpages(row) {
    this.service.getauditpage(row).subscribe(
      (res) => {
        this.total = res.count;
        this.row = res.row;
        this.options = res.rowoptions;
        this.getactivity(this.row, 0);
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  getactivity(row, page) {
    this.list = [];
    this.service.getactivity(row, page).subscribe(
      (res) => {
        this.list = res;
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  paginate(event) {
    this.getactivity(event.rows, event.page);
  }
}
