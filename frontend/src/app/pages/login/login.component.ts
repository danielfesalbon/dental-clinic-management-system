import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import { Validators, FormControl, FormGroup, FormBuilder, } from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    this.recovery = this.fb.group({
      username: new FormControl('', Validators.required),
      birthdate: new FormControl(new Date(), Validators.required)
    });
    this.user = this.fb.group({
      username: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(12)]),
      title: new FormControl(''),
      firstname: new FormControl('', Validators.required),
      lastname: new FormControl('', Validators.required),
      birthdate: new FormControl(new Date(), Validators.required),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      password2: new FormControl('', [Validators.required, Validators.minLength(8)])
    });
    this.credentials = this.fb.group({
      username: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(12)]),
      password: new FormControl('', Validators.required)
    });

    this.reset = this.fb.group({
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      password2: new FormControl('', [Validators.required, Validators.minLength(8)])
    });
    this.onsign();
  }

  tosignin: boolean;
  toregister: boolean;

  forgpass: boolean;
  resetpass: boolean;

  user: FormGroup;
  credentials: FormGroup;
  recovery: FormGroup;
  reset: FormGroup;

  onreg() {
    this.toregister = true;
    this.tosignin = false;
    this.forgpass = false;
    this.user.reset();
  }

  onsign() {
    this.credentials.reset();
    this.toregister = false;
    this.tosignin = true;
    this.forgpass = false;
  }

  usersignin(value: string) {
    let u = JSON.parse(JSON.stringify(value));
    this.service.userlogin(u.username, u.password).subscribe(
      (res) => {
        if (res.flag == 'Accepted' || res.flag == 'success') {
          this.tokenService.storeToken(res.token);
          this.tokenService.storeUser(res.username);
          this.router.navigate(['/back-office/main']);
        }
      },
      (err) => {
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.error.message, });
      }
    );
  }

  registernew(value: string) {
    let u = JSON.parse(JSON.stringify(value));
    this.service.saveuser(u).subscribe(
      (res) => {
        if (res.flag == 'success') {
          this.user.reset();
          this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event, });
        }
      },
      (err) => {
        this.user.reset();
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: 'Problem creating user account.', });
      }
    );
  }

  forgotpassword() {
    //this.user = {};
    this.forgpass = true;
    this.recovery.reset();
    this.reset.reset();
    this.reset.disable();
    //this.confpassword = '';
  }

  validateresetpass(value: string) {
    this.reset.disable();
    let u = JSON.parse(JSON.stringify(value));
    this.service.validateresetpassword(u).subscribe(
      (res) => {
        if (res.flag == 'success') {
          this.reset.enable();
          this.messageService.add({ key: 'bc', severity: 'success', summary: 'Valid', detail: res.event, });
        } else {
          this.reset.disable();
          this.messageService.add({
            key: 'bc', severity: 'error', summary: 'Failed', detail: 'User validation failed.',
          });
        }
      },
      (err) => {
        this.reset.disable();
        this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: 'User validation failed.', });
      }
    );
  }

  resetpassword(value: string) {
    let u = JSON.parse(JSON.stringify(value));
    if (u.password == u.password2) {
      this.confirmationService.confirm({
        key: 'regconf', message: 'Reset user password.',
        accept: () => {
          let r = JSON.parse(JSON.stringify(this.recovery.getRawValue()));
          u.username = r.username;
          this.service.resetpassword(u).subscribe(
            (res) => {
              if (res.flag == 'success') {
                this.messageService.add({ key: 'bc', severity: 'success', summary: 'Success', detail: res.event, });
                this.forgotpassword();
                this.forgpass = false;
              } else {
                this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: 'Reset failed.', });
              }
            },
            (err) => {
              this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: err.message, });
            }
          );
        },
      });
    } else {
      this.messageService.add({ key: 'bc', severity: 'error', summary: 'Failed', detail: "Reset credentials doesn't match" });
    }
  }
}
