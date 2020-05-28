import { Injectable } from "@angular/core";
import { HttpClient, HttpParams, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../../../../environments/environment'
import { Vehicle } from 'src/app/modules/shared/models/vehicle.model';
import { catchError } from 'rxjs/operators';

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
export class VehicleService {
  private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/vehicle';

    constructor(private http: HttpClient){}

    create(vehicle: Vehicle): Observable<any> {
        return this.http.post(this.baseUrl, vehicle).pipe(catchError(this.handleException));
    }

    upload(file: File[], id: number): Observable<any>{
        let formData = new FormData();
            for(let i = 0; i < file.length;i++){
            let formD : FormData;
            formData.append('file', file[i]);
            
            }
    
        return this.http.post(`${this.baseUrl}/upload/` + id, formData);
    }

    getVehicles(): Observable<Vehicle[]> {
        return this.http.get<Vehicle[]>(this.baseUrl, httpOptions);
    }

   getImages(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getImages/` + id);
   }  

    private handleException(err: HttpErrorResponse): Observable<never> {
        return throwError(err.error);
      }
}
