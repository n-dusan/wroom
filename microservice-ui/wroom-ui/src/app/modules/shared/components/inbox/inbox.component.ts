import { Component, OnInit } from '@angular/core';
import { MessageService } from 'src/app/modules/rents/services/message.service';
import { ToastrService } from 'ngx-toastr';
import { Message } from '../../models/message.model';

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

  constructor(private messageService: MessageService,
    private toastr: ToastrService) { }

  ngOnInit(): void {

    this.messageService.received().subscribe(
      data => {
        this.received = data;
      },
      error => {
        this.toastr.error('Unexpected error has ocurred', 'Error')
      }
    );

  }

  sentClick() {
    this.switched = true;
    this.messageService.sent().subscribe(
      data => {
        this.sent = data;
        console.log(data);
      },
      error => {
        this.toastr.error('Unexpected error has ocurred', 'Error')
      }
    );
  }

  receivedClick() {
    this.switched = false;
    this.messageService.received().subscribe(
      data => {
        this.received = data;
      },
      error => {
        this.toastr.error('Unexpected error has ocurred', 'Error')
      }
    );
  }

}
