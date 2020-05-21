import { Component, OnInit, Optional, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ModelType } from 'src/app/modules/shared/models/model-type.model';
import { ModelTypeService } from '../services/vehicle-features/model-type.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FeaturesOverviewComponent } from '../features-overview/features-overview.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-new-model-type',
  templateUrl: './new-model-type.component.html',
  styleUrls: ['./new-model-type.component.css']
})
export class NewModelTypeComponent implements OnInit {
  createForm: FormGroup;
  success = false;
  errorMessage = false
  modelType: ModelType = new ModelType();
  new: ModelType = new ModelType();


  constructor(private formBuilder: FormBuilder,
    private modelService: ModelTypeService,
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
    this.modelService.create(this.modelType)
      .subscribe(data => {
      this.toastr.success('You have successfully added Model Type!', 'Success')
    },

    error => 
      this.errorMessage = true);

  }

  onSubmit() {
    this.modelType.name = this.createForm.value.name;
    this.save();
  }


  back(){
    this.errorMessage = false;
    this.success = false;

  }

  onSubmitUpdate(id: number){


    this.modelService.update(id,this.local_data).subscribe(
      data => {
        this.new.name = this.modelUpdateForm.value.name;
      },
      error => console.log('Error!'));


  }

  modelUpdateForm = new FormGroup({
    name: new FormControl({ value: this.local_data.name, disabled: false}, Validators.required)
  });


}
