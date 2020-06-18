import { CanActivate } from '@angular/router';
import { Injectable } from '@angular/core';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AdsService } from '../services/ads.service';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class CreateAdGuard implements CanActivate {

  loggedUser: LoggedUser;

  constructor(private adsService: AdsService, private toastr: ToastrService) {}

  // canActivate(): Observable<boolean> {

  //   return this.adsService.checkAdCount().pipe(map((data: number) => {
  //     if(data < 3) {
  //       return true;
  //     } else {
  //       this.toastr.error('Ad limit exceeded (3)', 'Nope')
  //       return false;
  //     }
  //   }));
  // }

  canActivate(): boolean {

    return true;
  }
}
