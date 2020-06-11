import { Component, OnInit } from '@angular/core';
import { MessageService } from 'src/app/modules/rents/services/message.service';
import { ToastrService } from 'ngx-toastr';
import { Message } from '../../models/message.model';
import { AuthService } from '../../service/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { MessageDetailsComponent } from '../message-details/message-details.component';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

  typesOfShoes: string[] = ['Boots', 'Clogs', 'Loafers', 'Moccasins', 'Sneakers'];

  received: Message[] = [];
  sent: Message[] = [];

  switched: boolean = false;
  loaded: boolean = false;

  constructor(private messageService: MessageService,
    private authService: AuthService,
    private toastr: ToastrService,
    private dialog: MatDialog) { }

  ngOnInit(): void {

    this.messageService.received().subscribe(
      data => {
        this.received = data;
        console.log('messages', this.received)

        for(let request of this.received) {
          this.authService.get(request.fromUserId).subscribe(
            data => {
              request.fromUserNameSurname = data.name + ' ' + data.surname;
            },
            error => {
              this.toastr.error('Unexpected error has ocurred', 'Error')
            }
          );
        }

        this.loaded = true;
      },
      error => {
        this.loaded = true;
        this.toastr.error('Unexpected error has ocurred', 'Error')
      }
    );

  }

  sentClick() {
    this.switched = true;
    this.messageService.sent().subscribe(
      data => {
        this.sent = data;
        
        for(let request of this.sent) {
          this.authService.get(request.toUserId).subscribe(
            data => {
              request.toUserNameSurname = data.name + ' ' + data.surname;
            },
            error => {
              this.toastr.error('Unexpected error has ocurred', 'Error')
            }
          );
        }

        this.loaded = true;
      },
      error => {
        this.toastr.error('Unexpected error has ocurred', 'Error')
      }
    );
  }

  receivedClick() {
    this.switched = false;
    this.loaded = false;
    this.messageService.received().subscribe(
      data => {
        this.received = data;

        for(let request of this.received) {
          this.authService.get(request.fromUserId).subscribe(
            data => {
              request.fromUserNameSurname = data.name + ' ' + data.surname;
            },
            error => {
              this.toastr.error('Unexpected error has ocurred', 'Error')
            }
          );
        }

        this.loaded = true;
      },
      error => {
        this.loaded = true;
        this.toastr.error('Unexpected error has ocurred', 'Error')
      }
    );
  }

  openMessage(message: Message) {

    let dialogRef = this.dialog.open(MessageDetailsComponent,
      {
        data: {
          message: message
        }
      });

  }

}
