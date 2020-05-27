import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment'
import { Observable, throwError } from 'rxjs';
import { Location } from '../model/location.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class AdsService {

  private url = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/ads';

  constructor(private http: HttpClient) {}

  getAllLocations() : Observable<Location[]> {
    return this.http.get<Location[]>(this.url + '/location').pipe(catchError(this.handleException));
  }

  createLocation(location: Location) : Observable<Location> {
    return this.http.post<Location>(this.url + '/location', location).pipe(catchError(this.handleException));
  }

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }
}
