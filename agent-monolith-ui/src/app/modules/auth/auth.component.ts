import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { SignupRequest } from './model/signup-request.model';
import { AuthService } from './service/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { LoginRequest } from './model/login-request.model';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ValidationError } from './model/validation-error';
import { MatchingPassword } from './validators/matching-password.validator';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  initPage = true;

  signupForm: FormGroup;
  loginForm: FormGroup;

  login: boolean = true;

  errorMessage: ValidationError;

  constructor(private formBuilder: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {

    this.loginForm = this.formBuilder.group({
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'pass': new FormControl(null, Validators.required)
    });

    this.signupForm = this.formBuilder.group({
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'pass': new FormControl(null, Validators.required),
      'pass-rep': new FormControl(null, Validators.required),
      'name': new FormControl(null, Validators.required),
      'surname': new FormControl(null, Validators.required)
    },
    {
      validator: MatchingPassword('pass', 'pass-rep')
    });

  }


  signupSubmit() {
    const formValue = this.signupForm.value;
    const signupData = new SignupRequest(formValue.email, formValue.pass, formValue.name, formValue.surname);

    console.log(this.signupForm);

    this.authService.signup(signupData).subscribe(
      data => {
        this.initPage = false;
        this.toastr.success('Please check your E-Mail. We have sent you confirmation link.', 'Your request is sent!');
        this.router.navigate(['/home'])
      },
      error => {
        for(let er of error.errors) {
          this.toastr.error(er, 'Error')
        }
        
        console.log(error)
      }
    );
  }


  loginSubmit() {
    const request = new LoginRequest(this.loginForm.value.email, this.loginForm.value.pass);

    this.authService.login(request).subscribe(
      data => {
        this.initPage = false;
        this.toastr.success('You are now logged in!', 'Success');
        this.router.navigateByUrl('/home');
      },
      error => {
        this.errorMessage = error;
        console.log(this.errorMessage)
        this.toastr.error(error.errors, 'Error')
      }
    )
  }

  changePasswordClick() {
    const dialogRef = this.dialog.open(ChangePasswordComponent, {
      width: '500px',
      height: '400px',
      // data: {isAdd: this.isAdd=true }
    });

    // dialogRef.afterClosed().subscribe(result => {
    //   this.loadModelData();
    // });
    // console.log(this.isAdd)
  }

  testClick() {
    this.authService.test().subscribe(
      () => {},
      error => {
        this.toastr.error('You are not allowed to test that button!', 'Error')
      }
    );
  }
}
