import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';


class ImageSnippet {
  pending: boolean = false;
  status: string = 'init';
  constructor(public src: string, public file: File) {}
}

@Component({
  selector: 'app-making-vehicles-form',
  templateUrl: './making-vehicles-form.component.html',
  styleUrls: ['./making-vehicles-form.component.css']
})
export class MakingVehiclesFormComponent implements OnInit {
  isLinear = true;
  bar = false;
  success = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  selectedFile: ImageSnippet;

  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit() {
   
  }
 
  onNext1Click(){

  }

  selected = new FormControl('valid', [
    Validators.required,
    Validators.pattern('valid'),
  ]);

  urls = [];
  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
        var filesAmount = event.target.files.length;
        for (let i = 0; i < filesAmount; i++) {
                var reader = new FileReader();
                reader.onload = (event:any) => {
                  console.log(event.target.result);
                   this.urls.push(event.target.result); 
                }
                reader.readAsDataURL(event.target.files[i]);
        }
    }
  }

  doneClick(){
    this.bar = true;
    this.success = true;
  }
}
