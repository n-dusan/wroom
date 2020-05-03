import { Component, OnInit, Optional, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ModelType } from 'src/app/models/model-type.model';
import { ModelTypeService } from 'src/app/services/model-type.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FeaturesOverviewComponent } from '../features-overview/features-overview.component';

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
  

  constructor(private formBuilder: FormBuilder, private modelTypeService: ModelTypeService,
    private modelService: ModelTypeService,
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
    this.modelTypeService.create(this.modelType)
      .subscribe(data => {
        this.success = true;
      console.log(data);
      window.location.reload();
    },
   
    error => this.errorMessage = true);
   
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
        window.location.reload();
      },
      error => console.log('Error!'));
    
    
  }

  modelUpdateForm = new FormGroup({
    name: new FormControl({ value: this.local_data.name, disabled: false}, Validators.required)
  });

  
}
