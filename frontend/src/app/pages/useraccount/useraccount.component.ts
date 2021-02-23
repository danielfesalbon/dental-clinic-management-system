import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';


@Component({
  selector: 'app-useraccount',
  templateUrl: './useraccount.component.html',
  styleUrls: ['./useraccount.component.css'],
})
export class UseraccountComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private route: ActivatedRoute
  ) { }

  users: any[];
  usermodal: boolean;
  userform: FormGroup;
  user: any;

  genderoptions: any[];

  ngOnInit(): void {
    this.genderoptions = [{ value: 'M' }, { value: 'F' }];
    this.usermodal = false;
    this.userform = this.fb.group({
      username: new FormControl({ value: '', disabled: true }, Validators.required),
      title: new FormControl(''),
      firstname: new FormControl({ value: '', disabled: true }, Validators.required),
      lastname: new FormControl({ value: '', disabled: true }, Validators.required),
      middlename: new FormControl(''),
      email: new FormControl(''),
      contact: new FormControl('', Validators.required),
      address: new FormControl(''),
      gender: new FormControl(''),
      birthdate: new FormControl({ value: new Date(), disabled: true }, Validators.required),
      specialization: new FormControl('')
    });
    this.getusers();
  }

  viewuser(data) {
    this.user = data;
    let u: any = {};
    u.username = data.username;
    u.title = data.title;
    u.firstname = data.firstname;
    u.lastname = data.lastname;
    u.middlename = data.middlename;
    u.email = data.email;
    u.contact = data.contact;
    u.address = data.address;
    u.gender = this.genderoptions.find(i => i.value == data.gender);
    if (u.gender == undefined) {
      u.gender = null;
    }
    u.birthdate = new Date(data.birthdate);
    u.specialization = data.specialization;
    this.userform.setValue(u);
    this.usermodal = true;
  }

  getusers() {
    this.service.getusers().subscribe(
      (res) => {
        this.users = res;
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  getstatus(status) {
    if (status) {
      return 'Yes';
    } else {
      return 'No';
    }
  }

  saveuser(value: string) {
    let staff: any = JSON.parse(JSON.stringify(value));
    staff.gender = staff.gender.value;
    this.user.address = staff.address;
    this.user.contact = staff.contact;
    this.user.email = staff.email;
    this.user.gender = staff.gender;
    this.user.middlename = staff.middlename;
    this.user.specialization = staff.specialization;
    this.user.title = staff.title;
    this.confirmationService.confirm({
      message: 'Save user details.',
      accept: () => {
        this.service.updateuser(this.user).subscribe(
          (res) => {
            if (res.flag == 'success') {
              this.ngOnInit();
              this.usermodal = false;
              this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event, });
            }
          },
          (err) => {
            this.tokenService.checkSession(err);
            this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message, });
          }
        );
      },
    });
  }

  disableaccount(user) {
    let m = '';
    if (user.disabled) {
      m = 'Enable user.';
    } else {
      m = 'Disable user.';
    }
    this.confirmationService.confirm({
      message: m,
      accept: () => {
        if (user.disabled) {
          user.disabled = false;
        } else {
          user.disabled = true;
        }
        this.service.updateuser(user).subscribe(
          (res) => {
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

  deleteuser(user) {
    this.confirmationService.confirm({
      message: 'Delete user',
      accept: () => {
        this.service.deleteuser(user.userid).subscribe(
          (res) => {
            if (res.flag == 'success') {
              this.getusers();
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
