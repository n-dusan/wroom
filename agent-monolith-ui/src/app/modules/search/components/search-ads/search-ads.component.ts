import { Component, OnInit, ViewChild } from '@angular/core';
import { Ad } from '../../model/ad.model';
import { AdService } from '../../service/ad.service';
import { ToastrService } from 'ngx-toastr';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { AdDetailComponent } from '../ad-detail/ad-detail.component';
import { Vehicle } from '../../model/vehicle.model';
import { VehicleService } from '../../service/vehicle.service';
import { PriceList } from '../../model/price-list.model';
import { AdLocation } from '../../model/ad-location.model';
import { PriceListService } from '../../service/price-list.service';

@Component({
  selector: 'app-search-ads',
  templateUrl: './search-ads.component.html',
  styleUrls: ['./search-ads.component.css']
})
export class SearchAdsComponent implements OnInit {

  ads: Ad[] = [];
  vehicles: Vehicle[] = [];
  locations: AdLocation[] = [];
  priceLists: PriceList[] = [];

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private adService: AdService,
    private vehicleService: VehicleService,
    private pricelistService: PriceListService,
    private toastr: ToastrService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    // Fetching all necessary data
    this.adService.all().subscribe(
      data => {
        this.ads = data;
        console.log('ads', this.ads)
      },
      error => {
        this.toastr.error('There was an error!', 'Ads')
      }
    );

    this.vehicleService.all().subscribe(
      data => {
        this.vehicles = data;
        console.log('vehicles', this.vehicles)

        for (let v of this.vehicles) {
          var ad = this.ads.find(obj => { return obj.vehicleId === v.id });
          if(ad) {
            ad.vehicleObj = v;
          }
        }
      },
      error => {
        this.toastr.error('There was an error!', 'Vehicles')
      }
    );

    this.adService.getLocations().subscribe(
      data => {
        this.locations = data;
        console.log('locations', this.locations)

        for (let l of this.locations) {
          var ad = this.ads.find(obj => { return obj.locationId === l.id });
          if(ad) {
            ad.locationObj = l;
          }
        }
      },
      error => {
        this.toastr.error('There was an error!', 'Locations')
      }
    );

    this.pricelistService.all().subscribe(
      data => {
        this.priceLists = data;
        console.log('pricelists',this.priceLists)

        for (let p of this.priceLists) {
          var ad = this.ads.find(obj => { return obj.priceListId === p.id });
          if(ad) {
            ad.priceListObj = p;
          }
        }
      },
      error => {
        this.toastr.error('There was an error!', 'Pricelists')
      }
    );
    


  }


  openDetails(adID: number) {
    let dialogRef = this.dialog.open(AdDetailComponent,
      {
        data: { adID: adID }
      });
    // dialogRef.afterClosed().pipe(take(1)).subscribe((vehicle: Vehicle ) => {
    //   if(vehicle) {
    //     console.log('my vehicle', vehicle)
    //     this.vehicle = vehicle;
    //     this.adForm.get('vehicle').setValue('Selected ' + this.vehicle.brandType.name + ' ' + this.vehicle.modelType.name);
    //   }
    // });
  }
}
