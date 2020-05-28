import { Component, OnInit, Optional, Inject } from '@angular/core';
import { VehicleListComponent } from '../vehicle-list/vehicle-list.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { VehicleService } from '../services/vehicle-features/vehicle.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css']
})
export class VehicleDetailsComponent implements OnInit {
  private childSeats: any;
  imageData: any;
  imageURL: String;
  image: any;
  uRl : string;
  retrieveResonse: any;
  base64Data: any;
  retrievedImage: any;
  retrievedImageList: any[] = [];


  constructor(private formBuilder: FormBuilder,
              private vehicleService: VehicleService,
              public dialogRef: MatDialogRef<VehicleListComponent>,
              private sanitizer: DomSanitizer,
              @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }
  local_data = this.data;

  urls = [];
  ngOnInit(): void {
    this.childSeats = this.local_data.childSeats;
    this.vehicleService.getImages(this.local_data.id).subscribe(
      data => {
        this.retrieveResonse = data;
        for(let i = 0;i<this.retrieveResonse.length;i++){
          this.base64Data = this.retrieveResonse[i];
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
          this.retrievedImageList.push(this.retrievedImage);
        }

      }
          );
  }

  public getSantizeUrl(url : any) {

    return this.sanitizer.bypassSecurityTrustUrl(url);
}

}
