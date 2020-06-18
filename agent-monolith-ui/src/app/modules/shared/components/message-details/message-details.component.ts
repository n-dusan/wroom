import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ContactOwnerComponent } from 'src/app/modules/rents/components/contact-owner/contact-owner.component';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MessageService } from '../../service/message.service';
import { Message } from '../../models/message.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-message-details',
  templateUrl: './message-details.component.html',
  styleUrls: ['./message-details.component.css']
})
export class MessageDetailsComponent implements OnInit {

  form: FormGroup;
  message: Message;
  replyClicked = false;

  constructor(public dialogRef: MatDialogRef<ContactOwnerComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private toastr: ToastrService) { }

  ngOnInit(): void {

    this.message = this.data.message;

    this.form = this.formBuilder.group({
      'title': new FormControl(null),
      'content': new FormControl(null, [Validators.required])
    });

  }

  replyClick() {
    this.replyClicked = true;
  }

  send() {
    const title = this.message.title.includes('Reply') ? this.message.title : this.message.title + ' - Reply';
    console.log('message to reply to', this.message)
    const rep = new Message(
      null, //toUserId
      this.message.rentRequestId,  
      title, 
      this.form.value.content, 
      null, //fromUserId
      null, //date
      this.message.toUser, 
      this.message.fromUser )
    // const reply = new Message(this.message.fromUserId, this.message.rentRequestId, title,
    // this.form.value.content, this.message.toUserId, null, this.message.fromUserNameSurname);

    this.messageService.send(rep).subscribe(
      data => {
        console.log('sent', data);
        this.toastr.success('Message sent', 'Success')
        this.dialogRef.close();
      },
      error => {
        this.toastr.error('Sending faild', 'Error')
      }
    );

  }

}
