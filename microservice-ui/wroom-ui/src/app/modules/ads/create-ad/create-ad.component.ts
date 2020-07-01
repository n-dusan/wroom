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
import { GpsService } from '../../shared/service/gps.service';

@Component({
  selector: 'app-create-ad',
  templateUrl: './create-ad.component.html',
  styleUrls: ['./create-ad.component.css']
})
export class CreateAdComponent implements OnInit {

  locations: Location[] = [];
  priceList: PriceList;
  location: Location;
  vehicle: Vehicle;
  checked: boolean = false;
  updateRegime: boolean = false;

  adForm: FormGroup;
  selectedMileType: string = 'UNLIMITED';

  constructor(private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private adsService: AdsService,
    private toastr: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private gpsService: GpsService) { }

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

    if(this.activatedRoute.snapshot.params.id) {
      //take the id from the url
      let id = this.activatedRoute.snapshot.params.id;
        //first fetch the ad that belongs to the request id
        this.adsService.findAd(+id).subscribe((ad: Ad) => {

          this.updateRegime = true;

          if(ad.mileLimitEnabled) {
            this.selectedMileType = 'LIMITED'
          }

          //makes multiple http requests and waits for all of them to complete
          this.adsService.requestAdData(ad).subscribe(dataList => {

            let vehicleData = dataList[0];
            let priceListData = dataList[1];
            let locationData = dataList[2];
            console.log('vehicle data', vehicleData);
            console.log('pricelist data', priceListData);
            console.log('location data', locationData);

            this.vehicle = vehicleData;
            this.adForm.get('vehicle').setValue('Selected ' + this.vehicle.brandType.name + ' ' + this.vehicle.modelType.name);

            this.priceList = priceListData;
            this.adForm.get('priceList').setValue('Selected ' + this.priceList.pricePerDay + '$ per day');

            this.location = locationData;

            this.adForm.patchValue({
              address: ad.address,
              gps: ad.gps,
              mileLimit: ad.mileLimit,
              availableTo: ad.availableTo,
              availableFrom: ad.availableFrom,
              location: locationData
             });
          }, error => {
            this.toastr.error('Couldn\'t find location, vehicle or price-list associated with this ad', 'Aw snap');
          })


        }, error => {

          //should redirect to 404 page
          this.toastr.error('Couldn\'t find an Ad', 'Aw snap');
        })



    }

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
      ad.locationId = this.adForm.get('location').value.id;
      ad.address = this.adForm.get('address').value;
      ad.gps = this.adForm.get('gps').value;

      if(!this.updateRegime) {
        this.adsService.createAd(ad).subscribe((result:Ad) => {
          console.log(result)
          if(ad.gps) {
            this.gpsService.generateJWT(ad.vehicleId).subscribe(response => {
              console.log('yaay', response)
              localStorage.setItem(ad.vehicleId.toString(), response);
            });
          }
          this.toastr.success('Ya did it.', 'Aww yeah')
          this.router.navigate(['../overview'], { relativeTo: this.activatedRoute });
        }, error => {
          for(let er of error.errors) {
            this.toastr.error(er, 'Error')
          }
        });
      } else {
        ad.id = this.activatedRoute.snapshot.params.id
        this.adsService.updateAd(ad).subscribe((result: Ad) => {
          this.toastr.success('Ya did it.', 'Aww yeah')
          this.router.navigate(['../../overview'], { relativeTo: this.activatedRoute });
        }, error => {
          for(let er of error.errors) {
            this.toastr.error(er, 'Error')
          }
        })
      }
    }
  }

  refresh() {
    this.adsService.getAllLocations().subscribe( (data: Location[]) => {
      console.log('location data', data)
      this.locations = data;
    })
  }

  compareFunction(o1: any, o2: any) {
    return (o1.id == o2.id && o1.country == o2.country && o1.city == o2.city);
   }

}
