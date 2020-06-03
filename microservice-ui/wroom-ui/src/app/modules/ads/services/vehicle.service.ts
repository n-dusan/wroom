import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment'
import { Observable, throwError } from 'rxjs';
import { Vehicle } from '../../shared/models/vehicle.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';


@Injectable({providedIn: 'root'})
export class VehicleService {

  private vehicleUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/vehicle';

  constructor(private http: HttpClient) {}

  getVehicle(id: number): Observable<Vehicle> {
    return this.http.get<Vehicle>(this.vehicleUrl + '/' + id).pipe(catchError(this.handleException));
  }


  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }
}
