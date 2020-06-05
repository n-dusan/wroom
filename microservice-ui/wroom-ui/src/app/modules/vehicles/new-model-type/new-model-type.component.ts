import { Component, OnInit, Optional, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ModelType } from 'src/app/modules/shared/models/model-type.model';
import { ModelTypeService } from '../services/vehicle-features/model-type.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FeaturesOverviewComponent } from '../features-overview/features-overview.component';
import { ToastrService } from 'ngx-toastr';
import { BrandType } from '../../shared/models/brand-type.model';
import { BrandTypeService } from '../services/vehicle-features/brand-type.service';

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
  messageError: any;

  brands: BrandType[] = [];

  constructor(private formBuilder: FormBuilder,
    private modelService: ModelTypeService,
    private toastr: ToastrService,
    public dialogRef: MatDialogRef<FeaturesOverviewComponent>,
    //@Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private brandService: BrandTypeService) { }

  local_data = this.data;

  ngOnInit() {
    this.createForm = this.formBuilder.group({
      name: new FormControl(null, Validators.required),
      brand: new FormControl(null, Validators.required)
    });

    this.brandService.getBrandTypes().subscribe(
      data => {
        this.brands = data;
        console.log(this.brands)
      },
      error => {
        this.toastr.error('There was an error during fetching brand types.', 'Error')
      }
    );
  }

  save() {
    this.modelType.brandId = this.createForm.value.brand;
    console.log(this.modelType)
    this.modelService.create(this.modelType)
      .subscribe(data => {
      this.toastr.success('You have successfully added Model Type!', 'Success')
    },

    error => {
      this.toastr.error(error.errors, 'Error')
    });
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
