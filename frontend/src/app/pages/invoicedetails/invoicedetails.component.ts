import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-invoicedetails',
  templateUrl: './invoicedetails.component.html',
  styleUrls: ['./invoicedetails.component.css']
})
export class InvoicedetailsComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private route: ActivatedRoute
  ) { }


  invoice: any;
  invoicetx: any[];

  ngOnInit(): void {
    this.getinvoice();
  }


  getinvoice() {
    this.invoice = {};
    this.invoicetx = [];
    this.route.paramMap.subscribe(params => {
      this.service.getinvoice(params.get('id')).subscribe(res => {
        this.invoice = res.invoice;
        this.gettx(this.invoice.id);
      }, err => {
        this.tokenService.checkSession(err);
      });
    });
  }

  gettx(invoiceid) {
    this.service.gettransactions(invoiceid).subscribe(res => {
      this.invoicetx = res;
      console.log(this.invoicetx);
    }, err => {
      this.tokenService.checkSession(err);
    });
  }

}
