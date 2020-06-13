import { Component, OnInit, Optional, Inject } from '@angular/core';
import { AuthService } from '../../auth/service/auth.service';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Ad } from '../../ads/model/ad.model';
import { Ad as SharedAd } from '../../shared/models/ad.model';
import { AdsService } from '../../ads/services/ads.service';
import { Vehicle } from '../../shared/models/vehicle.model';
import { VehicleService } from '../../ads/services/vehicle.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdsOverviewComponent } from '../../ads/ads-overview/ads-overview.component';
import { RentRequest } from '../../shared/models/rent-request.model';
import { RentsService } from '../../rents/services/rents.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-vehicle-occupancy',
  templateUrl: './vehicle-occupancy.component.html',
  styleUrls: ['./vehicle-occupancy.component.css']
})
export class VehicleOccupancyComponent implements OnInit {
  loggedUser: LoggedUser;
  private ngUnsubscribe = new Subject();
  adsList: Ad[] = [];
  vehicleList: Vehicle[]=[];
  occupancyForm: FormGroup;
  listAds: Ad[] = [];

  constructor(private authService: AuthService,
              private adService: AdsService,
              private vehicleService: VehicleService,
              private formBuilder: FormBuilder,
              private rentsService: RentsService,
              private toastr: ToastrService,
              public dialogRef: MatDialogRef<AdsOverviewComponent>,
              @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.authService.whoami().subscribe(data => {
      this.loggedUser = data;
      this.adsList = [];
      this.refresh();
    },
      error => {
        console.log(error)
      }
    );
    this.occupancyForm = this.formBuilder.group({
      'selectAd': new FormControl(null, [Validators.required]),
      'availableFrom': new FormControl(null, [Validators.required]),
      'availableTo': new FormControl(null, [Validators.required])
    });
  }

  refresh() {
    this.adService.getAllActiveForUser(this.loggedUser.id).pipe(takeUntil(this.ngUnsubscribe)).subscribe((data: Ad[]) => {
      this.adsList = data;
      for(let ad of this.adsList){
        this.vehicleDetails(ad);
      }
    })
  }

  vehicleDetails(ad: Ad) {
    this.vehicleService.getVehicle(ad.vehicleId).subscribe( (vehicle: Vehicle) => {
        this.vehicleList.push(vehicle);

    })
  }

  save(rentRequest: RentRequest){
    this.rentsService.occupy(rentRequest).subscribe(
      data => {
        this.toastr.success('You have successfully add a vehicle occupancy request!', 'Success')

      },
      error => {
        this.toastr.error('Please enter a valid date according to the ad and pre-existing requests', 'Choosen date not valid')
      }
    );
  }

  onFormSubmit(){
      var status: string;
      const selectAd = this.occupancyForm.value.selectAd;
      const selectType = this.adsList.find(x => x.id == selectAd);
      const fromDate = this.occupancyForm.value.availableFrom;
      const toDate = this.occupancyForm.value.availableTo;
      this.listAds.push(selectType);

      //CONVERTER FOR OLJA -> ANA AD MODEL...
      let ad: SharedAd = new SharedAd();
      ad.address = selectType.address;
      ad.availableFrom = selectType.availableFrom;
      ad.availableTo = selectType.availableTo;
      ad.gps = selectType.gps;
      ad.id = selectType.id;
      ad.locationId = +selectType.locationId;
      ad.mileLimit = selectType.mileLimit;
      ad.mileLimitEnabled = selectType.mileLimitEnabled;
      ad.priceListId = selectType.priceListId;

      const newRentRequest = new RentRequest(null, status, fromDate, toDate, this.loggedUser.id, ad, null, null, null);
      this.save(newRentRequest);
  }



}
