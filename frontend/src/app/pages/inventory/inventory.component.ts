import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';


@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css'],
})
export class InventoryComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private sanitizer: DomSanitizer
  ) { }


  inventory: any[];
  total: any;
  row: any;
  page: any;
  options: any[];
  item: FormGroup;
  typeoptions: any[];


  ngOnInit(): void {
    this.typeoptions = [{ value: 'MEDICINE' }, { value: 'CLINIC TOOLS' }, { value: 'ESSENTIALS' }];
    this.item = this.fb.group({
      name: new FormControl('', Validators.required),
      description: new FormControl(''),
      type: new FormControl('', Validators.required),
      quantity: new FormControl(0, Validators.required),
      unitprice: new FormControl(0),
      supplier: new FormControl(''),
      itemcode: new FormControl('', Validators.required)
    });
    this.getinventorylist(10, 0);
  }


  onSubmit(value: string) {
    let i = JSON.parse(JSON.stringify(value));
    i.type = i.type.value;
    i.createdby = this.tokenService.getUser();
    this.confirmationService.confirm({
      message: 'Save new clinic item/inventory',
      accept: () => {
        this.service.saveinventory(i).subscribe(res => {
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

  getinventorylist(row, page) {
    this.service.getinventorylist(row, page).subscribe(res => {
      this.inventory = res.inventory;
      this.total = res.pagevalues.count;
      this.row = res.pagevalues.row;
      this.options = res.pagevalues.rowoptions;
      this.page = res.pagevalues.page;
    }, err => {
      this.tokenService.checkSession(err);
    });
  }

  viewinventory(id) {
    this.router.navigate(['back-office/main/inventory/' + id]);
  }

}
