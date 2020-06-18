import { Injectable } from '@angular/core';
import { SignupRequest } from '../model/signup-request.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable, Subscription, throwError } from 'rxjs';
import { LoggedUser } from '../model/logged-user.model';
import { LoginRequest } from '../model/login-request.model';
import { map, catchError } from 'rxjs/operators';
import { ResetPassword } from '../../shared/models/reset-password.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedUserSubject: BehaviorSubject<LoggedUser>;
  loggedUser: Observable<LoggedUser>;

  private baseUrl: string = environment.protocol
                        + '://' + environment.domain
                        + ':'
                        + environment.port
                        + environment.api
                        + environment.authService
                        + '/auth';



  private userUrl: string = environment.protocol
                      + '://' + environment.domain
                      + ':'
                      + environment.port
                      + environment.api
                      + environment.authService
                      + '/user';


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
      //console.log('login result;', res);
      localStorage.setItem('token', JSON.stringify(res.token));
      this.loggedUserSubject.next(res);
      this.loggedUser = this.loggedUserSubject.asObservable();
    }));
  }

  getLoggedUser() {
    return this.loggedUserSubject.asObservable();
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

  reauthenticate() {
    const tok = localStorage.getItem('token');
    if (tok) {
      this.httpClient.get<any>(this.baseUrl + '/whoami').subscribe(
        data => {
          this.loggedUserSubject.next(data)
          this.loggedUser = this.loggedUserSubject.asObservable();
          console.log('reauthenticated', this.loggedUserSubject)
        }
      );
    }
  }

  // fix this
  whoami() {
    const tok = localStorage.getItem('token');
    // console.log('whoami token', tok)
    if(tok) {
      // Set user to BehaviourSubject?
      this.httpClient.get<any>(this.baseUrl + '/whoami').subscribe(
        data => {
          this.loggedUserSubject.next(data);
          this.loggedUser = this.loggedUserSubject.asObservable();

          return this.httpClient.get<any>(this.baseUrl + '/whoami');
        },
        error => {
          console.log('Whoami error');
        }
      );
      return this.httpClient.get<any>(this.baseUrl + '/whoami');
    }
    else {
      this.router.navigate(['/auth']);
      return new Observable();
    }
  }

  confirmEmail(token: string) {
    return this.httpClient.put(this.baseUrl + '/confirm', token);
  }

  forgotPassword(email: string): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + '/forgot-password/' + email)
  }

  changePassword(token: ResetPassword): Observable<any> {
    return this.httpClient.put(this.baseUrl + '/reset-password', token);
  }

  getUser(userId: number): Observable<LoggedUser> {
    return this.httpClient.get<LoggedUser>(this.userUrl + '/' + userId);
  }

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }
}
