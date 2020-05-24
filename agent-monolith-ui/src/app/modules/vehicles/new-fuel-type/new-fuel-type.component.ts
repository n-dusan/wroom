import { Component, OnInit, Inject, Optional } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { FuelType } from 'src/app/modules/shared/models/fuel-type.model';
import { FuelTypeService } from '../services/vehicle-features/fuel-type.service';
import { Router } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FeaturesOverviewComponent } from '../features-overview/features-overview.component';
import { ToastrService } from 'ngx-toastr';

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
  new: FuelType = new FuelType();
  messageError: any;

  constructor(private formBuilder: FormBuilder, private fuelTypeService: FuelTypeService, private router: Router,
    private fuelService: FuelTypeService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<FeaturesOverviewComponent>,
    //@Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }

  local_data = this.data;

  ngOnInit(): void {
    this.createForm = this.formBuilder.group({
      name: new FormControl(null, Validators.required)
    });
  }

  save() {
    this.fuelTypeService.create(this.fuelType)
      .subscribe(data => {
        this.toastr.success('You have successfully added Fuel Type!', 'Success')
    },
    error => {
    this.errorMessage = true
    this.messageError = error;
    console.log(error)
    });
  }

  onSubmit() {
    this.fuelType.name = this.createForm.value.name;
    this.save();
  }

  back(){
    this.errorMessage = false;
    this.success = false;

  }

  onSubmitUpdate(id: number){
    this.fuelTypeService.update(id,this.local_data).subscribe(
      data => {
        this.new.name = this.fuelUpdateForm.value.name;
      },

      error => console.log('Error!'));
  }

  fuelUpdateForm = new FormGroup({
    name: new FormControl({ value: this.local_data.name, disabled: false}, Validators.required)
  });

}
