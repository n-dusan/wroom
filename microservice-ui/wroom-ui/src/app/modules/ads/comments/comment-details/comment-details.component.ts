import { Component, OnInit, Optional, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-comment-details',
  templateUrl: './comment-details.component.html',
  styleUrls: ['./comment-details.component.css']
})
export class CommentDetailsComponent implements OnInit {

  constructor(@Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }
  local_data = this.data;

  ngOnInit(): void {
  }

}
