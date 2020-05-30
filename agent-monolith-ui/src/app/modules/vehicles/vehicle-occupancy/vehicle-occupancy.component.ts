import { Component, OnInit, Optional, Inject } from '@angular/core';
import { AuthService } from '../../auth/service/auth.service';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Ad } from '../../ads/model/ad.model';
import { AdsService } from '../../ads/services/ads.service';
import { Vehicle } from '../../shared/models/vehicle.model';
import { VehicleService } from '../../ads/services/vehicle.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdsOverviewComponent } from '../../ads/ads-overview/ads-overview.component';

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

  constructor(private authService: AuthService,
              private adService: AdsService,
              private vehicleService: VehicleService,
              private formBuilder: FormBuilder,
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
      console.log('Ads by logged user', data)
    })
  }

  vehicleDetails(ad: Ad) {
    this.vehicleService.getVehicle(ad.vehicleId).subscribe( (vehicle: Vehicle) => {
        this.vehicleList.push(vehicle);
    
    })
  }

  onFormSubmit(){
      
  }

  

}
