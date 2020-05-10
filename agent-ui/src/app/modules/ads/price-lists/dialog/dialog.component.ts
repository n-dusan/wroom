import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { DialogRegimeEnum } from '../../model/dialog.enum'
import { PriceListService } from '../../services/price-list.service';
import { PriceList } from 'src/app/modules/shared/models/price-list.model';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  constructor(
    private priceListService: PriceListService,
    public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _formBuilder: FormBuilder) { }

  loadingData: boolean = true

  title: string;

  priceListForm: FormGroup;

  errorMessage: string = '';

  ngOnInit(): void {
    this.dialogRef.updateSize('50%','65%')

    if(this.data.type) {
      this.loadingData = false

      let initialPricePerMile = '';
      let initialPricePerDay = '';
      let initialPriceCDW = '';
      let initialDiscount = '';

      if(this.data.type === DialogRegimeEnum.CREATE) {
        this.title = 'New'
      } else {
        this.title = 'Edit'

        initialPricePerMile = this.data.priceList.pricePerMile;
        initialPricePerDay = this.data.priceList.pricePerDay;
        initialPriceCDW = this.data.priceList.priceCDW;
        initialDiscount = this.data.priceList.discount;

      }

      this.priceListForm = this._formBuilder.group({
        pricePerMile: [initialPricePerMile, Validators.required],
        pricePerDay: [initialPricePerDay, Validators.required],
        priceCDW: [initialPriceCDW],
        discount: [initialDiscount]
      });
    }


  }

  onFormSubmit() {

    //refresh errorMessage
    this.errorMessage = '';

     if(this.priceListForm.valid) {

      let pricePerDay = this.priceListForm.value.pricePerDay;
      let pricePerMile = this.priceListForm.value.pricePerMile;
      let priceCDW = this.priceListForm.value.priceCDW;
      let discount = this.priceListForm.value.discount;

      if(this.data.type === DialogRegimeEnum.CREATE) {

        //make a POST request

        this.priceListService.create(new PriceList(null, pricePerDay, pricePerMile, discount, priceCDW))
          .subscribe( (response: PriceList) => {
              console.log('success response', response);
              this.dialogRef.close();
          }, (error) => {
            this.errorMessage = error.message;
          });

        } else {
        //make a PUT request

        this.priceListService.update(new PriceList(this.data.priceList.id, pricePerDay, pricePerMile, discount, priceCDW))
        .subscribe( (response: PriceList) => {

            console.log('success response', response);
            this.dialogRef.close();
        }, (error) => {
          this.errorMessage = error.message;
        });

      }
    }
  }

}
