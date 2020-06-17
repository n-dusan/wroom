import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { AdsService } from '../../services/ads.service';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommentDetailsComponent } from '../comment-details/comment-details.component';
import { ToastrService } from 'ngx-toastr';
import { CommentModel } from 'src/app/modules/shared/models/comment-model.model';
import { VehicleService } from 'src/app/modules/shared/service/vehicle.service';
import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';
import { Ad } from 'src/app/modules/shared/models/ad.model';


@Component({
  selector: 'app-comments-overview',
  templateUrl: './comments-overview.component.html',
  styleUrls: ['./comments-overview.component.css']
})
export class CommentsOverviewComponent implements OnInit {
  displayedColumns: string[] = ['username', 'ad', 'comment', 'confirm', 'refuse'];
  dataCommentsSource : MatTableDataSource<any>;
  listAds: any[] = [];
  vehicleList: any[] = [];

  constructor(private adsService: AdsService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialog: MatDialog,
              private toastr: ToastrService,
              private vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.loadComments();
    this.loadAds();
    this.vehicleService.allVehicles().subscribe((data: Vehicle[]) => {
      this.vehicleList = data;
    })

  }

  loadComments(){
    this.adsService.getAllComments().subscribe(
      data => {
        console.log('comments', data)
        this.dataCommentsSource = new MatTableDataSource(data);
      }
    );
  }
  
  loadAds(){
    this.adsService.getAllAds().subscribe(
      data => {
        this.listAds = data;
        // console.log(this.listAds)        
      }
    );  
   
  }

  selectAd(adId: number){
    
    const ad = this.listAds.find(x => x.id == adId);
    
    const vehicle = this.vehicleList.find(x => x.id == ad.vehicleId); 
      const modelType = vehicle?.modelType?.name;  
      const brandType = vehicle?.brandType?.name;   
      const address = ad?.address
      const ret = brandType + " " + modelType + ", " + address; 
      return ret;
  }

  viewComment(comment:CommentModel){
    const dialogRef = this.dialog.open(CommentDetailsComponent, {
      width: '600px',
      height: '400px',
      data: comment
    });
    dialogRef.afterClosed().subscribe(result => {

    });  
  }

  confirm(comment: CommentModel){
    this.adsService.confirm(comment.id).subscribe(
      response => {
        this.loadComments();
        this.toastr.success('You have successfully approved comment!', 'Success')
    } 
    )
  }
  

  refuse(comment: CommentModel){
    this.adsService.refuse(comment.id).subscribe(
      response => {
        this.loadComments();
        this.toastr.success('You have successfully refused comment!', 'Success')
    } 
    )  
  }
  

}