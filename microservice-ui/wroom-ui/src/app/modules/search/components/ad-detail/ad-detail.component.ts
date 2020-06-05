import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdDetailDialogData } from './dialog-data.model';
import { AdService } from '../../service/ad.service';
import { ToastrService } from 'ngx-toastr';
import { Ad } from '../../model/ad.model';
import { VehicleService } from '../../service/vehicle.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Vehicle } from '../../model/vehicle.model';
import { PricelistDetailDialogData } from '../price-details/pricelist-dialog-data.model';
import { PriceList } from '../../model/price-list.model';
import { SearchService } from '../../service/search.service';

@Component({
  selector: 'app-ad-detail',
  templateUrl: './ad-detail.component.html',
  styleUrls: ['./ad-detail.component.css']
})
export class AdDetailComponent implements OnInit {

  ad: Ad;
  vehicle: Vehicle;
  images: string[] = [];
  currentImageIndex: number = 0;
  currentImage: string = "";
  priceData: PricelistDetailDialogData = {
    pricelistId: null,
    mileLimit: null,
    cdw: null
  };

  constructor(public dialogRef: MatDialogRef<AdDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AdDetailDialogData,
    private adService: AdService,
    private vehicleService: VehicleService,
    private toastr: ToastrService,
    public sanitizer: DomSanitizer,
    private searchService: SearchService) { }

  ngOnInit(): void {
    this.fetchAd();
  }

  fetchAd() {
    this.searchService.getAd(this.data.adID).subscribe(
      data => {
        this.ad = data;
        this.ad.priceListObj = this.data.pricelist;
        console.log(this.ad)
        this.fetchVehicle();
      },
      error => {
        this.toastr.error('There was an unexpected error during fetching choosen ad.', 'Error')
      }
    );
  }

  fetchVehicle() {
    this.searchService.getVehicle(this.ad.vehicleId).subscribe(
      data => {
        this.vehicle = data;
        this.fetchImages();
      },
      error => {
        this.toastr.error('There was an unexpected error.', 'Vehicle')
      }
    );
    
  }

  fetchImages() {
    this.searchService.getVehicleImages(this.ad?.vehicleId).subscribe(
      data => {
        data.forEach(obj => { this.images.push("data:image/jpeg;base64," + obj) });
        this.currentImage = this.images[0];
        this.initPricelist();
      },
      error => {
        this.toastr.error('There was an error.', 'Images');
      }
    );
  }

  initPricelist() {
    this.priceData.pricelistId = this.ad?.priceListId;
    this.priceData.mileLimit = this.ad?.mileLimit;
    this.priceData.cdw = this.vehicle?.cdw;
  }

  public getSantizeUrl() {
    return this.sanitizer.bypassSecurityTrustUrl(this.currentImage);
  }

  leftImageClick() {
    if(this.currentImageIndex > 0) {
      this.currentImageIndex = this.currentImageIndex - 1;
      this.currentImage = this.images[this.currentImageIndex];
    }
  }

  rightImageClick() {
    if(this.currentImageIndex < this.images.length - 1) {
      this.currentImageIndex = this.currentImageIndex + 1;
      this.currentImage = this.images[this.currentImageIndex];
    }
    else if(this.currentImageIndex == this.images.length - 1) {
      this.currentImageIndex = 0;
      this.currentImage = this.images[0];
    }
  }
}
