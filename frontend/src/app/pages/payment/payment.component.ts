import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';


@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  paymentform: FormGroup;
  invoiceamount: number;
  disabled: boolean;
  maxpayment: number;

  ngOnInit(): void {
    this.disabled = false;
    this.maxpayment = 0;
    this.paymentform = this.fb.group({
      invoiceid: new FormControl('', Validators.required),
      amount: new FormControl(0, Validators.required),
      transactiondate: new FormControl(new Date(), Validators.required),
      receivedby: new FormControl({ value: '', disabled: true }, Validators.required),
      paidby: new FormControl('', Validators.required),
      invoiceamount: new FormControl({ value: 0, disabled: true }, Validators.required),
      paidamount: new FormControl({ value: 0, disabled: true }, Validators.required)
    });
  }

  searchinvoice(value: string) {
    this.invoiceamount = 0;
    let search = JSON.parse(JSON.stringify(value));
    this.service.getinvoice(search.invoiceid).subscribe(res => {
      let i: any = {};
      let r = res.invoice;
      this.disabled = res.invoice.amount > res.invoice.paid;
      i.invoiceid = res.invoice.id;
      i.amount = null;
      i.transactiondate = new Date();
      i.receivedby = this.tokenService.getUser();
      i.paidby = '';
      i.invoiceamount = r.amount;
      i.paidamount = r.paid;
      this.maxpayment = res.invoice.amount - res.invoice.paid;
      this.paymentform.setValue(i);
    }, err => {
      this.tokenService.checkSession(err);
    });
  }


  onSubmit(value: string) {
    let submit = JSON.parse(JSON.stringify(value));
    this.confirmationService.confirm({
      message: 'New Transaction',
      accept: () => {
        submit.receivedby = this.tokenService.getUser();
        this.service.savepayment(submit).subscribe(res => {
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
