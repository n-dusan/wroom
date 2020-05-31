import { Injectable } from "@angular/core";
import { HttpClient, HttpParams, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { RentRequest } from '../../shared/models/rent-request';

const httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  };

@Injectable({
    providedIn: 'root'
})
export class RentsService {
  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/rents';

    constructor(private http: HttpClient){}

    occupy(rentRequest: RentRequest): Observable<any> {
        return this.http.post(this.baseUrl, rentRequest);
    }

}
