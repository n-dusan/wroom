import { Component, OnInit, Optional, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Comment } from 'src/app/modules/shared/models/comment.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdsService } from '../../services/ads.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-new-reply',
  templateUrl: './new-reply.component.html',
  styleUrls: ['./new-reply.component.css']
})
export class NewReplyComponent implements OnInit {

  addReply: FormGroup;
  comment: Comment = new Comment();

  constructor( @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
               private formBuilder: FormBuilder,
               private adsService: AdsService,
               private toastr: ToastrService) {

               }
  local_data = this.data;

  ngOnInit(): void {
    this.addReply = this.formBuilder.group({
      content: new FormControl(null, Validators.required)
    });

  }

  save() {
    this.adsService.addReply(this.comment, this.local_data.comment.id)
      .subscribe(data => {
      this.toastr.success('Your comment is being processed.', 'Success')
    },

    error => {
      this.toastr.error('Error!', 'Error')
    });
  }

  onSubmit() {
    this.comment.content = this.addReply.value.content;

    this.save();
  }

}
