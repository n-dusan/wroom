import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { BodyType } from 'src/app/models/body-type.model';
import { BodyTypeService } from 'src/app/services/body-type.service';

@Component({
  selector: 'app-new-body-type',
  templateUrl: './new-body-type.component.html',
  styleUrls: ['./new-body-type.component.css']
})
export class NewBodyTypeComponent implements OnInit {
  createForm: FormGroup;
  success = false;
  bodyType: BodyType = new BodyType();
  
  constructor(private formBuilder: FormBuilder, private bodyTypeService: BodyTypeService) { }

  ngOnInit(): void {
    this.createForm = this.formBuilder.group({
      name: new FormControl(null, Validators.required)
    });
  }

  save() {  
    this.bodyTypeService.create(this.bodyType)
      .subscribe(data => {
      this.success = true;
      console.log(data);
    },
    error => console.log("Error !"));
  }

  onSubmit() {
    this.bodyType.name = this.createForm.value.name;
    this.save();
  }

}
