import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { PriceListSelectComponent } from '../price-lists/price-list-select/price-list-select.component';
import { MatDialog } from '@angular/material/dialog';
import { take } from 'rxjs/operators'
import { VehicleListSelectComponent } from './vehicle-list-select/vehicle-list-select.component';
import { Location } from '../model/location.model';
import { CreateCityComponent } from '../create-city/create-city.component';
import { AdsService } from '../services/ads.service';
import { PriceList } from '../../shared/models/price-list.model';

@Component({
  selector: 'app-create-ad',
  templateUrl: './create-ad.component.html',
  styleUrls: ['./create-ad.component.css']
})
export class CreateAdComponent implements OnInit {

  locations: Location[] = [];
  priceList: PriceList;

  adForm: FormGroup;
  selectedMileType: string = 'UNLIMITED';

  constructor(private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private adsService: AdsService) { }

  ngOnInit(): void {

    this.adForm = this.formBuilder.group({
      'vehicle': new FormControl(null, [Validators.required]),
      'priceList': new FormControl({ value: 'Not selected', disabled: true}, [Validators.required]),
      'availableFrom': new FormControl(null, [Validators.required]),
      'availableTo': new FormControl(null, [Validators.required]),
      'mileLimit': new FormControl(null, [Validators.required]),
      'location': new FormControl(null, [Validators.required]),
      'address': new FormControl(null, [Validators.required])
    });

    this.adForm.get('location').valueChanges.subscribe((value) => {
      console.log('my value', value)
    })

    this.refresh();
  }

  openVehicleModal() {
    //TODO
    let dialogRef = this.dialog.open(VehicleListSelectComponent);
    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
    });
  }

  openPriceListModal() {
    let dialogRef = this.dialog.open(PriceListSelectComponent);
    dialogRef.afterClosed().pipe(take(1)).subscribe((result: PriceList) => {
      if(result) {
        this.priceList = result;
        this.adForm.get('priceList').setValue('Selected ' + this.priceList.pricePerDay + '$ per day');
        console.log('set value', this.adForm.get('priceList').value)
      }
    });
  }

  newLocation() {
    let dialogRef = this.dialog.open(CreateCityComponent);
    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      this.refresh()
    });
  }


  onFormSubmit() {

  }

  refresh() {
    this.adsService.getAllLocations().subscribe( (data: Location[]) => {
      console.log('location data', data)
      this.locations = data;
    } )
  }

}
