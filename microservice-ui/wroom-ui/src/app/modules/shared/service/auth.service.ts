import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable, Subscription, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { LoggedUser } from '../../auth/model/logged-user.model';
import { SignupRequest } from '../../auth/model/signup-request.model';
import { LoginRequest } from '../../auth/model/login-request.model';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedUserSubject: BehaviorSubject<LoggedUser> = new BehaviorSubject<LoggedUser>(null);
  loggedUser: Observable<LoggedUser>;

  private baseUrl: string = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + environment.authService + '/auth';
  private userUrl: string = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + environment.authService + '/user';


  constructor(private httpClient: HttpClient,
    private router: Router) {
      this.loggedUserSubject = new BehaviorSubject<LoggedUser>(JSON.parse(localStorage.getItem('LoggedUser')));
      this.loggedUser = this.loggedUserSubject.asObservable();
  }

  signup(data: SignupRequest): Observable<SignupRequest> {
    console.log(this.baseUrl);
    return this.httpClient.post<SignupRequest>(this.baseUrl + '/signup', data).pipe(catchError(this.handleException));
  }

  login(data: LoginRequest): Observable<any> {
    return this.httpClient.post<any>(this.baseUrl + '/login', data).pipe(catchError(this.handleException)).pipe(map((res: LoggedUser) => {
      localStorage.setItem('token', JSON.stringify(res.token));
      this.loggedUserSubject.next(res);
      // console.log(this.loggedUser)
    }));
  }

  getLoggedUser() {
    return this.loggedUser;
  }

  getToken() {
      return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.loggedUserSubject.next(null);
    this.loggedUser = this.loggedUserSubject.asObservable();
    this.router.navigate(['/auth']);
  }

  whoami() : Observable<LoggedUser> {
    const tok = localStorage.getItem('token');
    // console.log('whoami token', tok)
    if(tok) {
      return this.httpClient.get<LoggedUser>(this.baseUrl + '/whoami');
    }
    else {
      this.router.navigate(['/auth']);
      return new Observable();
    }
  }

  confirmEmail(token: string) {
    return this.httpClient.put(this.baseUrl + '/confirm', token);
  }


  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }

  get(id: number): Observable<User> {
    return this.httpClient.get(this.userUrl + '/' + id);
  }
}
