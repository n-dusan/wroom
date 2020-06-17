import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { CommentModel } from 'src/app/modules/shared/models/comment.model';
import { MatTableDataSource } from '@angular/material/table';
import { AdsService } from '../../services/ads.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommentDetailsComponent } from '../comment-details/comment-details.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-comments-overview',
  templateUrl: './comments-overview.component.html',
  styleUrls: ['./comments-overview.component.css']
})
export class CommentsOverviewComponent implements OnInit {
  displayedColumns: string[] = ['username', 'ad', 'comment', 'confirm', 'refuse'];
  dataCommentsSource : MatTableDataSource<any>;


  constructor(private adsService: AdsService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialog: MatDialog,
              private toastr: ToastrService) { }

  ngOnInit(): void {
    this.loadComments();
  }

  loadComments(){
    this.adsService.getAllComments().subscribe(
      data => {
        this.dataCommentsSource = new MatTableDataSource(data);
        console.log(data[0].title)
      }
    );
  }

  viewComment(comment:CommentModel){
    const dialogRef = this.dialog.open(CommentDetailsComponent, {
      width: '500px',
      height: '250px',
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
