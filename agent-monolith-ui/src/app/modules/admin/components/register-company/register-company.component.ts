import { Component, OnInit, Injectable } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Company } from 'src/app/modules/shared/models/company.model';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { ToastrService } from 'ngx-toastr';



@Component({
  selector: 'app-register-company',
  templateUrl: './register-company.component.html',
  styleUrls: ['./register-company.component.css']
})
export class RegisterCompanyComponent implements OnInit {
  registerForm: FormGroup;
  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private toastr: ToastrService) {
   
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      email: ['', Validators.required],
      name: ['', Validators.required],
      website: ['', Validators.required],
      address: ['', Validators.required],
      businessNumber: ['', Validators.required]
    })
  }

  save(company: Company){
    this.authService.register(company).subscribe(
      data => {
        this.toastr.success('You have successfully registered a company')
      },
      error => {
        this.toastr.error('Fields must not be empty or invalid')
      }
    );  
  }

  registerSubmit(){
    const email = this.registerForm.value.email;
    const name = this.registerForm.value.name;
    const website = this.registerForm.value.website;
    const address = this.registerForm.value.address;
    const businessNumber = this.registerForm.value.businessNumber;
    const enabled = false;
    const nonLocked = false;

    const company = new Company(name, website, email, enabled, nonLocked, businessNumber, address);
    console.log(company)
    this.save(company);
  }

}
