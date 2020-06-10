import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/shared/service/auth.service';
import { LoggedUser } from 'src/app/modules/auth/model/logged-user.model';
import { RentsService } from '../../services/rents.service';

@Component({
  selector: 'app-requests-overview',
  templateUrl: './requests-overview.component.html',
  styleUrls: ['./requests-overview.component.css']
})
export class RequestsOverviewComponent implements OnInit {

  loggedUser: LoggedUser;
  loaded: boolean = false;

  constructor(private authService: AuthService,
    private rentsService: RentsService) { }

  ngOnInit(): void {

    this.authService.whoami().subscribe(data => {
      this.loggedUser = data;

      this.rentsService.getRequestedForUser(this.loggedUser.id).subscribe(
        data => {
          console.log(data)
          this.loaded = true;
        },
        error => {
          this.loaded = true;
  
        }
      )
    });

    


  }

}
