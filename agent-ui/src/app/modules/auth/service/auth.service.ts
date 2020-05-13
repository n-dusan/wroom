import { Injectable } from '@angular/core';
import { SignupRequest } from '../model/signup-request.model';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoggedUser } from '../model/logged-user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedUserSubject: BehaviorSubject<LoggedUser>;
  loggedUser: Observable<LoggedUser>;

  private baseUrl: string = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/auth'

  constructor(private httpClient: HttpClient,
              private router: Router) { }

  signup(data: SignupRequest): Observable<SignupRequest> {
    console.log(this.baseUrl);
    return this.httpClient.post<SignupRequest>(this.baseUrl + '/signup', data);
  }
}
