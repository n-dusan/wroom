import { Component, OnInit, Optional, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { BodyType } from 'src/app/modules/shared/models/body-type.model';
import { BodyTypeService } from '../services/vehicle-features/body-type.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FeaturesOverviewComponent } from '../features-overview/features-overview.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-new-body-type',
  templateUrl: './new-body-type.component.html',
  styleUrls: ['./new-body-type.component.css']
})
export class NewBodyTypeComponent implements OnInit {
  createForm: FormGroup;
  success = false;
  errorMessage = false
  bodyType: BodyType = new BodyType();
  new: BodyType = new BodyType();

  constructor(private formBuilder: FormBuilder, private bodyTypeService: BodyTypeService,
    private bodyService: BodyTypeService,
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
    this.bodyTypeService.create(this.bodyType)
      .subscribe(data => {
      this.toastr.success('You have successfully added Body Type!', 'Success')
    },
    error => this.errorMessage = true);
  }

  onSubmit() {
    this.bodyType.name = this.createForm.value.name;
    this.save();
  }

  back(){
    this.errorMessage = false;
    this.success = false;

  }

  onSubmitUpdate(id: number){
    this.bodyTypeService.update(id,this.local_data).subscribe(
      data => {
        this.new.name = this.bodyUpdateForm.value.name;
      },
      error => console.log('Error!'));

  }

  bodyUpdateForm = new FormGroup({
    name: new FormControl({ value: this.local_data.name, disabled: false}, Validators.required)
  });

}
