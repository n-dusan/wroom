import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/shared/service/auth.service';
import { LoggedUser } from 'src/app/modules/auth/model/logged-user.model';
import { RentsService } from '../../services/rents.service';
import { RentRequest } from 'src/app/modules/shared/models/rent-request.model';
import { MatDialog } from '@angular/material/dialog';
import { ContactOwnerComponent } from '../contact-owner/contact-owner.component';

@Component({
  selector: 'app-requests-overview',
  templateUrl: './requests-overview.component.html',
  styleUrls: ['./requests-overview.component.css']
})
export class RequestsOverviewComponent implements OnInit {

  loggedUser: LoggedUser;
  loaded: boolean = false;

  pending: RentRequest[] = [];
  reserved: RentRequest[] = [];
  paid: RentRequest[] = [];
  canceled: RentRequest[] = [];

  constructor(private authService: AuthService,
    private rentsService: RentsService,
    private dialog: MatDialog) { }

  ngOnInit(): void {

    this.authService.whoami().subscribe(data => {
      this.loggedUser = data;

      this.rentsService.getRequestedForUser(this.loggedUser.id).subscribe(
        data => {
          console.log(data)

          for (let request of data) {
            if (request.status === 'PENDING') {
              this.pending.push(request);
            }
            else if (request.status === 'RESERVED') {
              this.reserved.push(request);
            }
            else if (request.status === 'PAID') {
              this.paid.push(request);
            }
            else if (request.status === 'CANCELED') {
              this.canceled.push(request);
            }
          }

          this.loaded = true;
        },
        error => {
          this.loaded = true;

        }
      )
    });
  }

  sendMessage(request: RentRequest) {

    console.log(request)
    let dialogRef = this.dialog.open(ContactOwnerComponent,
      {
        data: {
          request: request
        }
      });

  }

}
