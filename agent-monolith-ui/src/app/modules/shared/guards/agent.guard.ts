import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { find, map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable()
export class AgentGuard implements CanActivate {

  loggedUser: LoggedUser;

  constructor(private router: Router,
    private authService: AuthService) {
  }

  canActivate(): Observable<boolean> {
    return this.authService.whoami().pipe(map((data: LoggedUser) => {
      var isAdmin = data.privileges.find(obj => {return obj === "ROLE_AGENT"});
      if(isAdmin) {
        return true;
      } else {
        return false;
      }
    }));
  }

}