import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AdsService } from '../../services/ads.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommentDetailsComponent } from '../comment-details/comment-details.component';
import { ToastrService } from 'ngx-toastr';
import { VehicleService } from 'src/app/modules/shared/service/vehicle.service';
import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';
import { CommentModel } from 'src/app/modules/shared/models/comment-model.model';
import { UserService } from 'src/app/modules/admin/services/user.service';

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
  userList: any[] = [];

  constructor(private adsService: AdsService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialog: MatDialog,
              private toastr: ToastrService,
              private vehicleService: VehicleService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.loadComments();
    this.loadAds();
    this.vehicleService.allVehicles().subscribe((data: Vehicle[]) => {
      this.vehicleList = data;
    })
    this.userService.findAllEnabled().subscribe((data: any[]) => {
      this.userList = data;
    })


  }

  loadComments(){
    this.adsService.getAllComments().subscribe(
      data => {
        this.dataCommentsSource = new MatTableDataSource(data);
      }
    );
  }
  
  loadAds(){
    this.adsService.getAllAds().subscribe(
      data => {
        this.listAds = data;       
      }
    );  
   
  }

  getUsername(username: String){
    const user = this.userList.find(x => x.email == username);  
    return user?.name + " " + user?.surname;
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

  viewComment(comment:Comment){
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
