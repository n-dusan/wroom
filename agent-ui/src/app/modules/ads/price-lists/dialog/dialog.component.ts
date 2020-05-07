import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _formBuilder: FormBuilder) { }

  loadingData: boolean = true

  title: string;

  priceListForm: FormGroup;

  ngOnInit(): void {

    this.dialogRef.updateSize('50%','65%')

    console.log(`Dialog data: `, this.data);
    if(this.data.type) {
      this.loadingData = false
      if(this.data.type === 'create') {
        this.title = 'New'
      } else {
        this.title = 'Edit'
      }
    }

    this.priceListForm = this._formBuilder.group({
      pricePerMile: ['', Validators.required],
      pricePerDay: ['', Validators.required],
      priceCDW: [''],
      discount: ['']
    });
  }

  onFormSubmit() {

  }

}
