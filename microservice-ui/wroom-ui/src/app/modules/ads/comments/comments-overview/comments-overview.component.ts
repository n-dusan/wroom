import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { CommentModel } from 'src/app/modules/shared/models/comment.model';
import { MatTableDataSource } from '@angular/material/table';
import { AdsService } from '../../services/ads.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-comments-overview',
  templateUrl: './comments-overview.component.html',
  styleUrls: ['./comments-overview.component.css']
})
export class CommentsOverviewComponent implements OnInit {
  displayedColumns: string[] = ['username', 'comment', 'confirm', 'refuse'];
  dataCommentsSource : MatTableDataSource<any>;


  constructor(private adsService: AdsService) { }

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

  }
  

}
