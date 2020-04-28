import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { FuelType } from 'src/app/models/fuel-type.model';
import { FuelTypeService } from 'src/app/services/fuel-type.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-fuel-type',
  templateUrl: './new-fuel-type.component.html',
  styleUrls: ['./new-fuel-type.component.css']
})
export class NewFuelTypeComponent implements OnInit {
  createForm: FormGroup;
  success = false;
  errorMessage = false
  fuelType: FuelType = new FuelType();

  constructor(private formBuilder: FormBuilder, private fuelTypeService: FuelTypeService, private router: Router) { }

  ngOnInit(): void {
    this.createForm = this.formBuilder.group({
      name: new FormControl(null, Validators.required)
    });
  }

  save() {  
    this.fuelTypeService.create(this.fuelType)
      .subscribe(data => {
      this.success = true;
      console.log(data);
    },
    error => this.errorMessage = true);
  }

  onSubmit() {
    this.fuelType.name = this.createForm.value.name;
    this.save();
  }

  back(){
    this.errorMessage = false;
    this.success = false;
    
  }

}
