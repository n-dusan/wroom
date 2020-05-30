import { CanActivate } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from '../../auth/service/auth.service';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AdsService } from '../services/ads.service';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class CreateAdGuard implements CanActivate {

  loggedUser: LoggedUser;

  constructor(private authService: AuthService, private adsService: AdsService, private toastr: ToastrService) {}

  canActivate(): Observable<boolean> {

    return this.whoami().pipe(map((user: LoggedUser )=> {
        this.adsService.checkAdCount(user.id).pipe(map(result => {
          if(result < 3) {
            return true;
          }
        }))
        this.toastr.error('Cant read ads', 'Cant');
        return false;
    }))
  }

  whoami() {
    return this.authService.whoami().pipe(map((user:LoggedUser) => {
      console.log('i am GUARD', user)
      return user;
    }))
  }
}
