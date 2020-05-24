import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { SignupRequest } from './model/signup-request.model';
import { AuthService } from './service/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { LoginRequest } from './model/login-request.model';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  signupForm: FormGroup;
  loginForm: FormGroup;

  login: boolean = true;

  constructor(private formBuilder: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router) { }

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
    });

  }


  signupSubmit() {
    const formValue = this.signupForm.value;
    const signupData = new SignupRequest(formValue.email, formValue.pass, formValue.name, formValue.surname);

    this.authService.signup(signupData).subscribe(
      data => {
        this.toastr.success('Your request is sent!', 'Success')
      },
      error => {
        this.toastr.error('There was an error with your request!', 'Error')
        console.log(error)
      }
    );
  }


  loginSubmit() {
    const request = new LoginRequest(this.loginForm.value.email, this.loginForm.value.pass);

    this.authService.login(request).subscribe(
      data => {
        this.toastr.success('You are now logged in!', 'Success');
        this.router.navigateByUrl('/home');
      },
      error => {
        this.toastr.error(error.error, 'Error')
        console.log(error)
      }
    )
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
