import { Component, OnInit } from '@angular/core';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-inventorydetails',
  templateUrl: './inventorydetails.component.html',
  styleUrls: ['./inventorydetails.component.css']
})
export class InventorydetailsComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private route: ActivatedRoute
  ) { }


  inventory: FormGroup;
  invdetails: any;
  typeoptions: any[];

  ngOnInit(): void {
    this.typeoptions = [{ value: 'MEDICINE' }, { value: 'CLINIC TOOLS' }, { value: 'ESSENTIALS' }];
    this.inventory = this.fb.group({
      type: new FormControl('', Validators.required),
      description: new FormControl(''),
      quantity: new FormControl(0),
      unitprice: new FormControl(0),
      supplier: new FormControl(''),
      itemcode: new FormControl('', Validators.required),
    });
    this.getinventorydetails();
  }

  getinventorydetails() {
    this.invdetails = {};
    this.route.paramMap.subscribe(params => {
      this.service.getinventory(params.get('id')).subscribe(res => {
        this.invdetails = res.inventory;
        let inv: any = {};
        inv.type = this.typeoptions.find(t => t.value == this.invdetails.type);
        inv.quantity = this.invdetails.quantity;
        inv.unitprice = this.invdetails.unitprice;
        inv.supplier = this.invdetails.supplier;
        inv.itemcode = this.invdetails.itemcode;
        inv.description = this.invdetails.description;
        this.inventory.setValue(inv);
      }, err => {
        this.tokenService.checkSession(err);
      });

    });
  }


  onSubmit(value: string) {
    let prod: any = JSON.parse(JSON.stringify(value));
    prod.type = prod.type.value;
    this.invdetails.type = prod.type;
    this.invdetails.quantity = prod.quantity;
    this.invdetails.unitprice = prod.unitprice;
    this.invdetails.supplier = prod.supplier;
    this.invdetails.itemcode = prod.itemcode;
    this.invdetails.description = prod.description;
    this.invdetails.updatedby = this.tokenService.getUser();
    this.confirmationService.confirm({
      message: 'Update inventory details',
      accept: () => {
        this.service.updateinventory(this.invdetails).subscribe(res => {
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
