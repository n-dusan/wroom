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
import { Vehicle } from '../../shared/models/vehicle.model';
import { Ad } from '../model/ad.model';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-ad',
  templateUrl: './create-ad.component.html',
  styleUrls: ['./create-ad.component.css']
})
export class CreateAdComponent implements OnInit {

  locations: Location[] = [];
  priceList: PriceList;
  vehicle: Vehicle;
  checked: boolean = false;

  adForm: FormGroup;
  selectedMileType: string = 'UNLIMITED';

  constructor(private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private adsService: AdsService,
    private toastr: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.adForm = this.formBuilder.group({
      'vehicle': new FormControl({ value: 'Not selected', disabled: true } , [Validators.required]),
      'priceList': new FormControl({ value: 'Not selected', disabled: true }, [Validators.required]),
      'availableFrom': new FormControl(null, [Validators.required]),
      'availableTo': new FormControl(null, [Validators.required]),
      'mileLimit': new FormControl(null, [Validators.nullValidator]),
      'location': new FormControl(null, [Validators.required]),
      'address': new FormControl(null, [Validators.required]),
      'gps': new FormControl(false, [Validators.required])
    });

    this.adForm.get('location').valueChanges.subscribe((locationId) => {
      console.log('my value', locationId)
    })

    this.refresh();
  }

  openVehicleModal() {
    //TODO
    let dialogRef = this.dialog.open(VehicleListSelectComponent);
    dialogRef.afterClosed().pipe(take(1)).subscribe((vehicle: Vehicle ) => {
      if(vehicle) {
        console.log('my vehicle', vehicle)
        this.vehicle = vehicle;
        this.adForm.get('vehicle').setValue('Selected ' + this.vehicle.brandType.name + ' ' + this.vehicle.modelType.name);
      }
    });
  }

  openPriceListModal() {
    let dialogRef = this.dialog.open(PriceListSelectComponent);
    dialogRef.afterClosed().pipe(take(1)).subscribe((priceList: PriceList) => {
      if(priceList) {
        this.priceList = priceList;
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

    if(this.adForm.valid) {
      console.log('valid')
      console.log('date from ', this.adForm.get('availableFrom').value)
      console.log('date to ', new Date(this.adForm.get('availableTo').value))
      console.log('vehicle', this.adForm.get('vehicle').value)
      console.log('price', this.adForm.get('priceList').value)
      console.log('mileLimit ', this.adForm.get('mileLimit').value)
      console.log('location  ', this.adForm.get('location').value)
      console.log('address  ', this.adForm.get('address').value)
      console.log('gps  ', this.adForm.get('gps').value)

      let ad: Ad = new Ad();
      ad.vehicleId = this.vehicle.id;
      ad.priceListId = this.priceList.id;
      ad.availableFrom = new Date(this.adForm.get('availableFrom').value);
      ad.availableTo = new Date(this.adForm.get('availableTo').value);
      if(this.selectedMileType === 'UNLIMITED') {
        ad.mileLimitEnabled = false;
      } else {
        ad.mileLimitEnabled = true;
        ad.mileLimit = this.adForm.get('mileLimit').value;
      }
      ad.locationId = this.adForm.get('location').value;
      ad.address = this.adForm.get('address').value;
      ad.gps = this.adForm.get('gps').value;

      this.adsService.createAd(ad).subscribe((result:Ad) => {
        console.log(result)
        this.toastr.success('Ya did it.', 'Aww yeah')
        this.router.navigate(['../overview'], { relativeTo: this.activatedRoute });
      }, error => {
        for(let er of error.errors) {
          this.toastr.error(er, 'Error')
        }
      });
    }
  }

  refresh() {
    this.adsService.getAllLocations().subscribe( (data: Location[]) => {
      console.log('location data', data)
      this.locations = data;
    })
  }

}
