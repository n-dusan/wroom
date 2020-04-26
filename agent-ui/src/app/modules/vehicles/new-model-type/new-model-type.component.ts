import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ModelType } from 'src/app/models/model-type.model';
import { ModelTypeService } from 'src/app/services/model-type.service';

@Component({
  selector: 'app-new-model-type',
  templateUrl: './new-model-type.component.html',
  styleUrls: ['./new-model-type.component.css']
})
export class NewModelTypeComponent implements OnInit {
  createForm: FormGroup;  
  spin = false;
  success = false;
  modelType: ModelType = new ModelType();

  constructor(private formBuilder: FormBuilder, private modelTypeService: ModelTypeService) { }

  ngOnInit() {
    this.createForm = this.formBuilder.group({
      name: new FormControl(null, Validators.required)
    });
  }

  save() {  
    this.spin = true;
    this.modelTypeService.create(this.modelType)
      .subscribe(data => {
        this.spin = false;
        this.success = true;
      console.log(data);
    },
    error => console.log("Error !"));
    this.spin = false;
  }

  onSubmit() {
    this.modelType.name = this.createForm.value.name;
    this.save();
  }

  
}
