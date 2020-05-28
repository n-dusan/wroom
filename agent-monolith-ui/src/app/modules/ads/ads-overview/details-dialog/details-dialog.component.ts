import { Component, OnInit, Inject } from '@angular/core';
import { PriceListService } from '../../services/price-list.service';
import { AdsService } from '../../services/ads.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PriceList } from 'src/app/modules/shared/models/price-list.model';
import { Location } from '../../model/location.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-details-dialog',
  templateUrl: './details-dialog.component.html',
  styleUrls: ['./details-dialog.component.css']
})
export class DetailsDialogComponent implements OnInit {


  priceList: PriceList;
  location: Location;

  constructor(private priceListService: PriceListService,
    private adsService: AdsService,
    public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    if(this.data.type === 'PRICE_LIST') {

      this.priceListService.findById(this.data.id).subscribe( (priceList: PriceList) => {
        this.priceList = priceList;
      }, error => this.toastr.error("Something went wrong with data fetching" + error, "Aw snap"))
    }
    else if(this.data.type === 'LOCATION') {

      this.adsService.findLocation(this.data.id).subscribe( (location: Location ) => {
        this.location = location;
      }, error => this.toastr.error("Something went wrong with data fetching" + error, "Aw snap"))
    }
  }

}
