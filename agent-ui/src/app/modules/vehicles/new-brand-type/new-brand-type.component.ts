import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { BrandType } from 'src/app/models/brand-type.model';
import { BrandTypeService } from 'src/app/services/brand-type.service';

@Component({
  selector: 'app-new-brand-type',
  templateUrl: './new-brand-type.component.html',
  styleUrls: ['./new-brand-type.component.css']
})
export class NewBrandTypeComponent implements OnInit {
  createForm: FormGroup;
  success = false;
  brandType: BrandType = new BrandType();
  
  constructor(private formBuilder: FormBuilder, private brandTypeService: BrandTypeService) { }

  ngOnInit(): void {
    this.createForm = this.formBuilder.group({
      name: new FormControl(null, Validators.required)
    });
  }

  save() {  
    this.brandTypeService.create(this.brandType)
      .subscribe(data => {
      this.success = true;
      console.log(data);
    },
    error => console.log("Error !"));
  }

  onSubmit() {
    this.brandType.name = this.createForm.value.name;
    this.save();
  }

}
