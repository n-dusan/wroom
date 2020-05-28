import { Component, OnInit, ViewChild } from '@angular/core';
import { Ad } from '../../model/ad.model';
import { AdService } from '../../service/ad.service';
import { ToastrService } from 'ngx-toastr';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { AdDetailComponent } from '../ad-detail/ad-detail.component';

@Component({
  selector: 'app-search-ads',
  templateUrl: './search-ads.component.html',
  styleUrls: ['./search-ads.component.css']
})
export class SearchAdsComponent implements OnInit {

  ads: Ad[] = [];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private adService: AdService,
    private toastr: ToastrService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.adService.all().subscribe(
      data => {
        this.ads = data;
        console.log(data);
      },
      error => {
        this.toastr.error('There was an error!', 'Error')
      }
    );

    // this.ads.push(new Ad(1, 1, 1, new Date(2020,5,15),new Date(2020,6,15), 120, true, 1, 'Bulevar oslobodjenja 8', true));
  }


  openDetails(adID: number) {
    let dialogRef = this.dialog.open(AdDetailComponent,
      {
        data: {adID: adID}
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
