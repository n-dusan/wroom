import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment'
import { Observable, throwError } from 'rxjs';
import { Location } from '../model/location.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Vehicle } from '../../shared/models/vehicle.model';
import { Ad } from '../model/ad.model';

@Injectable({providedIn: 'root'})
export class AdsService {

  private adsUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/ads';
  private vehicleUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/vehicle';

  constructor(private http: HttpClient) {}

  createAd(ad: Ad): Observable<Ad> {
    return this.http.post<Ad>(this.adsUrl, ad).pipe(catchError(this.handleException));
  }

  getAllLocations() : Observable<Location[]> {
    return this.http.get<Location[]>(this.adsUrl + '/location').pipe(catchError(this.handleException));
  }

  createLocation(location: Location) : Observable<Location> {
    return this.http.post<Location>(this.adsUrl + '/location', location).pipe(catchError(this.handleException));
  }


  getAllVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.vehicleUrl).pipe(catchError(this.handleException));
  }

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }
}
