import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  signupForm: FormGroup;
  loginForm: FormGroup;

  login: boolean = true;

  constructor(private formBuilder: FormBuilder) { }

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

}
