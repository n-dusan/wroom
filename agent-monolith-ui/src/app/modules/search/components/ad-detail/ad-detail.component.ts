import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdDetailDialogData } from './dialog-data.model';
import { AdService } from '../../service/ad.service';
import { ToastrService } from 'ngx-toastr';
import { Ad } from '../../model/ad.model';

@Component({
  selector: 'app-ad-detail',
  templateUrl: './ad-detail.component.html',
  styleUrls: ['./ad-detail.component.css']
})
export class AdDetailComponent implements OnInit {

  ad: Ad;

  constructor(public dialogRef: MatDialogRef<AdDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AdDetailDialogData,
    private adService: AdService,
    private toastr: ToastrService) { }

  ngOnInit(): void {

    this.adService.get(this.data.adID).subscribe(
      data => {
        console.log(data)
        this.ad = data;
      },
      error => {
        this.toastr.error('There was an unexpected error during fetching choosen ad.', 'Error')
      }
    );

  }

}
