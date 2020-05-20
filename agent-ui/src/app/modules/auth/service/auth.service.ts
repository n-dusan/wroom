import { Injectable } from '@angular/core';
import { SignupRequest } from '../model/signup-request.model';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoggedUser } from '../model/logged-user.model';
import { LoginRequest } from '../model/login-request.model';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedUserSubject: BehaviorSubject<LoggedUser>;
  loggedUser: Observable<LoggedUser>;

  private baseUrl: string = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/auth'

  constructor(private httpClient: HttpClient,
    private router: Router) {
      this.loggedUserSubject = new BehaviorSubject<LoggedUser>(JSON.parse(localStorage.getItem('LoggedUser')));
      this.loggedUser = this.loggedUserSubject.asObservable();
  }

  signup(data: SignupRequest): Observable<SignupRequest> {
    console.log(this.baseUrl);
    return this.httpClient.post<SignupRequest>(this.baseUrl + '/signup', data);
  }

  login(data: LoginRequest): Observable<any> {
    return this.httpClient.post<any>(this.baseUrl + '/login', data).pipe(map((res: LoggedUser) => {
      localStorage.setItem('token', JSON.stringify(res.token));
      this.loggedUserSubject.next(res);
      console.log(this.loggedUser)
    }));
  }

  getLoggedUser() {
    return this.loggedUser;
  }

  getToken() {
    return this.loggedUserSubject.value.token;
  }

  logout() {
    localStorage.removeItem('token');
    this.loggedUserSubject.next(null);
    this.loggedUser = this.loggedUserSubject.asObservable();
    this.router.navigate(['/auth']);
  }

  test(): Observable<string> {
    return this.httpClient.get<string>('http://localhost:8081/api/stub' + '/test-auth');
  }
}