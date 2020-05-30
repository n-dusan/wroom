import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { PriceList } from '../../model/price-list.model';
import { PriceListService } from '../../service/price-list.service';
import { PricelistDetailDialogData } from './pricelist-dialog-data.model';

@Component({
  selector: 'app-price-details',
  templateUrl: './price-details.component.html',
  styleUrls: ['./price-details.component.css']
})
export class PriceDetailsComponent implements OnInit {

  pricelist: PriceList;
  mileLimit: number;

  constructor(public dialogRef: MatDialogRef<PriceDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PricelistDetailDialogData,
    private pricelistService: PriceListService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.pricelistService.get(this.data.pricelistId).subscribe(
      data => {
        console.log(this.data)
        this.pricelist = data;
        this.mileLimit = this.data.mileLimit;
      },
      () => {
        this.toastr.error('There was an unexpected error during fetching choosen ad.', 'Error')
      }
    );
  }


  close() {
    this.dialogRef.close();
  }
}


