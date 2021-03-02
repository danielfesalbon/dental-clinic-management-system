import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService,
    private route: ActivatedRoute
  ) { }

  usermodal: boolean;
  user: any;
  position: string;
  themeicon: string;
  mode: string;
  confpassword: string;
  letter: string;
  name: string;
  userform: FormGroup;

  genderoptions: any[];
  bottombar: boolean;

  ngOnInit(): void {
    this.bottombar = false;
    this.name = '';
    this.letter = '?';
    this.confpassword = '';
    this.position = 'top';
    this.usermodal = false;
    this.genderoptions = [{ value: 'M' }, { value: 'F' }];
    this.user = {};
    this.userform = this.fb.group({
      title: new FormControl(''),
      email: new FormControl('', Validators.email),
      contact: new FormControl('', Validators.required),
      address: new FormControl(''),
      gender: new FormControl(''),
      birthdate: new FormControl({ value: new Date(), disabled: true }, Validators.required),
      specialization: new FormControl('')
    });
    this.getuser();
  }

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    let pass = group.get('password').value;
    let confirmPass = group.get('confirmPass').value;

    return pass === confirmPass ? null : { notSame: true }
  }

  showprofile() {
    this.usermodal = true;
    this.confpassword = '';
    this.user.password = '';
  }

  onlogout() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to log out?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.logout();
      },
    });
  }

  getuser() {
    this.service.getuser(this.tokenService.getUser()).subscribe(
      (res) => {
        this.user = res.useraccount;
        let u: any = {};
        u.title = this.user.title;
        u.contact = this.user.contact;
        u.email = this.user.email;
        u.address = this.user.address;
        u.gender = this.genderoptions.find(i => i.value == this.user.gender);
        u.birthdate = new Date(this.user.birthdate);
        u.specialization = this.user.specialization;

        this.letter = this.user.firstname.charAt(0).toUpperCase() + this.user.lastname.charAt(0).toUpperCase();
        this.name = this.user.title + ' ' + this.user.firstname + ' ' + this.user.lastname;
        this.userform.setValue(u);
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  changepassword() {
    this.confirmationService.confirm({
      message: 'Update password',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.service.changepassword(this.user).subscribe(
          (res) => {
            if (res.flag == 'success') {
              this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event, });
              this.ngOnInit();
            }
          },
          (err) => {
            this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message, });
            this.tokenService.checkSession(err);
          }
        );
      },
    });
  }

  logout() {
    this.service.userlogout(this.tokenService.getUser()).subscribe(
      (res) => {
        if (res.flag == 'success') {
          this.tokenService.destroy();
          location.reload();
        }
      },
      (err) => {
        this.tokenService.checkSession(err);
      }
    );
  }

  saveuser(value: string) {
    let staff: any = JSON.parse(JSON.stringify(value));
    staff.gender = staff.gender.value;
    this.user.address = staff.address;
    this.user.contact = staff.contact;
    this.user.email = staff.email;
    this.user.gender = staff.gender;
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

}
