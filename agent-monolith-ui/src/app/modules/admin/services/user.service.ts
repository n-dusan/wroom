import { Injectable } from "@angular/core";
import { environment } from '../../../../environments/environment'
import { User } from '../model/user.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class UserService {

  userUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/user';

  constructor(private http: HttpClient) {}

  findAllEnabled(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl).pipe(catchError(this.handleException));
  }

  activateUser(id: number): Observable<User> {
    return this.http.put<User>(this.userUrl + '/unlock/' + id, {}).pipe(catchError(this.handleException));
  }

  lockUser(id: number): Observable<User> {
    return this.http.put<User>(this.userUrl + '/lock/' + id, {}).pipe(catchError(this.handleException));
  }

  deleteUser(id: number) {
    return this.http.delete(this.userUrl + '/' + id, { responseType: 'text' }).pipe(catchError(this.handleException));
  }


  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }

}
