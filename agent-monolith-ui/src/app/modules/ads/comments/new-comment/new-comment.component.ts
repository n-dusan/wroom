import { Component, OnInit, Optional, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommentModel } from 'src/app/modules/shared/models/comment-model.model';

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.css']
})
export class NewCommentComponent implements OnInit {
  addComment: FormGroup;
  comment: CommentModel = new CommentModel();
  constructor( @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
               private formBuilder: FormBuilder) { }
  local_data = this.data;

  ngOnInit(): void {
    this.addComment = this.formBuilder.group({
      title: new FormControl(null, Validators.required),
      content: new FormControl(null, Validators.required)
    });
  }

  save() {
    console.log(this.comment.title)
  }

  onSubmit() {
    this.comment.title = this.addComment.value.title;
    this.comment.content = this.addComment.value.content;
    this.save();
  }

}
