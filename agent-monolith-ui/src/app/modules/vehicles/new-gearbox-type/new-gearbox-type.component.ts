import { Component, OnInit, Optional, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { GearboxType } from '../../shared/models/gearbox-type.model';
import { GearboxTypeService } from '../services/vehicle-features/gearbox-type.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FeaturesOverviewComponent } from '../features-overview/features-overview.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-new-gearbox-type',
  templateUrl: './new-gearbox-type.component.html',
  styleUrls: ['./new-gearbox-type.component.css']
})
export class NewGearboxTypeComponent implements OnInit {
  createForm: FormGroup;
  success = false;
  errorMessage = false
  gearboxType: GearboxType = new GearboxType();
  new: GearboxType = new GearboxType();
  messageError: any;

  constructor(private formBuilder: FormBuilder,
    private gearboxService: GearboxTypeService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<FeaturesOverviewComponent>,
    //@Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }

  local_data = this.data;

  ngOnInit() {
    this.createForm = this.formBuilder.group({
      name: new FormControl(null, Validators.required)
    });

  }

  save() {
    this.gearboxService.create(this.gearboxType)
      .subscribe(data => {
        this.toastr.success('You have successfully added Gearbox Type!', 'Success')
    },

    error =>{ 
      this.toastr.error(error.errors, 'Error')});

  }

  onSubmit() {
    this.gearboxType.name = this.createForm.value.name;
    this.save();
  }


  back(){
    this.errorMessage = false;
    this.success = false;

  }

  onSubmitUpdate(id: number){
    this.gearboxService.update(id,this.local_data).subscribe(
      data => {
        this.new.name = this.gearboxUpdateForm.value.name;
      },
      error => console.log('Error!'));
  }

  gearboxUpdateForm = new FormGroup({
    name: new FormControl({ value: this.local_data.name, disabled: false}, Validators.required)
  });


}
