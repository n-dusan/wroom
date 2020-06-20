import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { BodyType } from '../../../shared/models/body-type.model';
import { environment } from '../../../../../environments/environment'
import { catchError } from 'rxjs/operators';
@Injectable({
    providedIn: 'root'
})
export class BodyTypeService {
    private baseUrl = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + environment.vehicleService + '/body-type';

    constructor(private http: HttpClient){}

    create(bodyType: BodyType): Observable<any> {
        return this.http.post(this.baseUrl + "", bodyType).pipe(catchError(this.handleException));
    }

    getBodyTypes(): Observable<any> {
        return this.http.get(this.baseUrl);
    }

    delete(id: number): Observable<any>{
        return this.http.delete(`${this.baseUrl}/${id}`);
    }

    update(id: number, value: any){
        return this.http.put(`${this.baseUrl}/${id}`, value);
    }

    private handleException(err: HttpErrorResponse): Observable<never> {
        return throwError(err.error);
      }

}
