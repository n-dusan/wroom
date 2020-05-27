import { Component, OnInit } from '@angular/core';
import { AdsService } from '../services/ads.service';
import { MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '../model/location.model';

@Component({
  selector: 'app-create-city',
  templateUrl: './create-city.component.html',
  styleUrls: ['./create-city.component.css']
})
export class CreateCityComponent implements OnInit {

  loadingData: boolean = true;

  cityForm: FormGroup;

  constructor(
    private adsService: AdsService,
    public dialogRef: MatDialogRef<any>,
    private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.cityForm = this._formBuilder.group({
      country: ['', Validators.required],
      city: ['', Validators.required]
    });
  }

  onFormSubmit() {
    if(this.cityForm.valid) {
      let country = this.cityForm.value.country;
      let city = this.cityForm.value.city;
      console.log('country' + country + 'city ' + city);
      let l: Location = new Location(null, country, city);
      console.log('L is', l);
      this.adsService.createLocation(new Location(null, country, city)).subscribe((response) => {
        console.log('wat i got', response)
      })

      // this.adsService.getAllLocations().subscribe((response: Location) => {
      //   console.log('wat i got', response)
      // })
    }

  }
}
