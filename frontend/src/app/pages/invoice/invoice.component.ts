import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';


@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css'],
})
export class InvoiceComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  invoiceitem: any[];
  invoice: any;
  totalamount: number;

  inventory: any[];
  invtotal: any;
  invrow: any;
  invpage: any;
  invoptions: any[];

  searchitem: FormGroup;
  patientform: FormGroup;
  customform: FormGroup;

  invoicelist: any[];
  invoicetotal: any;
  invoicerow: any;
  invoicepage: any;
  invoiceoptions: any[];

  ngOnInit(): void {
    this.invoice = {};
    this.invoiceitem = [];
    this.invoice.amount = 0;
    this.invoice.paid = 0;
    this.inventory = [];
    this.invoicelist = [];
    this.searchitem = this.fb.group({
      name: new FormControl('', Validators.required),
    });
    this.patientform = this.fb.group({
      ptid: new FormControl(),
      ptname: new FormControl('', Validators.required),
      ptcontact: new FormControl('', Validators.required),
      ptaddress: new FormControl('', Validators.required)
    });

    this.customform = this.fb.group({
      name: new FormControl('', Validators.required),
      quantity: new FormControl(),
      totalamount: new FormControl(null, Validators.required)
    });
    this.getinvoicerecords(10, 0);
  }

  getinvoicerecords(row, page) {
    this.service.getinvoicelist(row, page).subscribe(res => {
      this.invoicelist = res.invoicelist;
      this.invoicetotal = res.pagevalues.count;
      this.invoicerow = res.pagevalues.row;
      this.invoiceoptions = res.pagevalues.rowoptions;
      this.invoicepage = res.pagevalues.page;
    }, err => {
      this.tokenService.checkSession(err);
    });
  }

  paginateInvoice(event) {
    this.invoicepage = event.page;
    this.invoicerow = event.rows;
    this.getinvoicerecords(this.invoicerow, this.invoicepage);
  }

  invpaginate(event) {
    this.invpage = event.page;
    this.invrow = event.rows;

  }

  addquantity(data) {
    data.quantity = +data.quantity;
    if (data.quantity + 1 <= data.stock) {
      data.quantity = data.quantity + 1;
    }
  }

  subtractquantity(data) {
    data.quantity = +data.quantity;
    if (data.quantity - 1 > 0) {
      data.quantity = data.quantity - 1;
    }
  }

  addtocart(data) {
    let prod: any = {};
    if (this.inventory.length != 0) {
      prod = this.invoiceitem.find((i) => {
        return i.inventoryid == data.id;
      });
    } else {
      prod = null;
    }
    if (prod != undefined && prod != null) {
      let quantity = +this.invoiceitem.find((i) => i.inventoryid == data.id).quantity;
      quantity = +quantity + +data.stock;
      if (+quantity <= +data.quantity) {
        this.invoiceitem.find((i) => i.inventoryid == data.id).quantity = quantity;
        this.invoiceitem.find((i) => i.inventoryid == data.id).totalamount = +quantity * +data.unitprice;
        this.messageService.add({
          key: 'bc', severity: 'success', summary: 'Item added', detail: data.quantity + ' ' + data.name + ' was added.',
        });
      } else {
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Invalid routine', detail: "Stock and quantity doesn't match", });
        //insert toast invalid routine -> stock : quantity validation
      }
    } else {
      prod = {};
      prod.inventoryid = data.id;
      prod.name = data.name;
      prod.unitprice = data.unitprice;
      prod.quantity = data.stock;
      prod.totalamount = +data.stock * +data.unitprice;
      if (+data.stock <= +data.quantity) {
        this.invoiceitem.push(prod);
        this.messageService.add({ key: 'bc', severity: 'success', summary: 'Item added', detail: data.quantity + ' ' + data.name + ' was added.', });
      } else {
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Invalid routine', detail: "Stock and quantity doesn't match", });
        //insert toast invalid routine -> stock : quantity validation
      }
    }
    //data.quantity = 1;
    this.invoice.amount = 0;
    this.invoiceitem.forEach((i) => {
      this.invoice.amount = this.invoice.amount + +i.totalamount;
    });
  }

  onSubmit(value: string) {
    let patient = JSON.parse(JSON.stringify(value));
    if (patient.ptid != null) {
      this.invoice.ptid = patient.ptid;
    }
    this.invoice.ptname = patient.ptname;
    this.invoice.ptcontact = patient.ptcontact;
    this.invoice.ptaddress = patient.ptaddress;
    this.invoice.createdby = this.tokenService.getUser();
    let param: any = {};
    param.invoice = this.invoice;
    param.items = this.invoiceitem;
    this.confirmationService.confirm({
      message: 'Create new invoice',
      accept: () => {
        this.service.saveinvoice(param).subscribe(res => {
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

  onAdd(value: string) {
    let prod = JSON.parse(JSON.stringify(value));
    this.customform.reset();
    this.invoiceitem.push(prod);
    this.invoice.amount = 0;
    this.invoiceitem.forEach((i) => {
      this.invoice.amount = this.invoice.amount + +i.totalamount;
    });
    this.messageService.add({ key: 'bc', severity: 'success', summary: 'Item added', detail: prod.name + ' was added.', });
  }

  searchItem(value: string) {
    let item = JSON.parse(JSON.stringify(value));
    this.service.wildsearchinventory(10, 0, item.name).subscribe(res => {
      this.inventory = res.list;
      this.inventory.forEach(i => { i.stock = 1; });
      this.invtotal = res.pagevalues.count;
      this.invrow = res.pagevalues.row;
      this.invoptions = res.pagevalues.rowoptions;
      this.invpage = res.pagevalues.page;
    }, err => {
      this.tokenService.checkSession(err);
      this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });
    });
  }


  removeItem(item) {
    this.invoiceitem = this.invoiceitem.filter(res => res.name != item.name);
    this.invoice.amount = 0;
    this.invoiceitem.forEach(i => {
      this.invoice.amount = this.invoice.amount + +i.totalamount;
    });
  }

  searchPatient(value: string) {
    this.patientform.reset();
    let p = JSON.parse(JSON.stringify(value));
    this.service.getpatient(p.ptid, 10, 0, 10, 0, 10, 0).subscribe(res => {
      let patient = res.patient;
      this.patientform.setValue({ ptid: patient.id, ptname: patient.firstname + " " + patient.lastname, ptcontact: patient.contact, ptaddress: patient.address })
      this.messageService.add({ key: 'bc', severity: 'info', summary: 'Success', detail: 'Patient data fetched' });
    }, err => {
      this.tokenService.checkSession(err);
      if (err.status == 404) {
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: 'Not found' });
      } else {
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message });
      }
    });
  }

  viewinvoice(id) {
    this.router.navigate(['back-office/main/invoice/' + id]);
  }
}
