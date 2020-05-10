import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { PriceListSelectComponent } from '../price-lists/price-list-select/price-list-select.component';
import { MatDialog } from '@angular/material/dialog';
import { take, takeUntil } from 'rxjs/operators'
import { VehicleListSelectComponent } from './vehicle-list-select/vehicle-list-select.component';

@Component({
  selector: 'app-create-ad',
  templateUrl: './create-ad.component.html',
  styleUrls: ['./create-ad.component.css']
})
export class CreateAdComponent implements OnInit {


  form: FormGroup;
  

  constructor(private formBuilder: FormBuilder,
    private dialog: MatDialog) { }

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      'vehicle': new FormControl(null, [Validators.required]),
      'priceList': new FormControl(null, [Validators.required]),
      'availableFrom': new FormControl(null, [Validators.required]),
      'availableTo': new FormControl(null, [Validators.required]),
      'mileLimit': new FormControl(null, [Validators.required]),
    });

  }

  openVehicleModal() {
    //TODO
    let dialogRef = this.dialog.open(VehicleListSelectComponent);
    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
    });
  }

  openPriceListModal() {
    let dialogRef = this.dialog.open(PriceListSelectComponent);
    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
    });
  }

}
